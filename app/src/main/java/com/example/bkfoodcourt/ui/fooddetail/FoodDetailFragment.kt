package com.example.bkfoodcourt.ui.fooddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andremion.counterfab.CounterFab
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.bkfoodcourt.Common.Common
import com.example.bkfoodcourt.Database.CartDataSource
import com.example.bkfoodcourt.Database.CartDatabase
import com.example.bkfoodcourt.Database.CartItem
import com.example.bkfoodcourt.Database.LocalCartDataSource
import com.example.bkfoodcourt.EventBus.CountCartEvent
import com.example.bkfoodcourt.Model.CommentModel
import com.example.bkfoodcourt.Model.FoodModel
import com.example.bkfoodcourt.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.layout_rating_comment.*
import java.lang.StringBuilder
import dmax.dialog.SpotsDialog
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class FoodDetailFragment : Fragment() {

    private lateinit var foodDetailViewModel: FoodDetailViewModel

    private val compositeDisposable = CompositeDisposable()
    private lateinit var  cartDataSource:CartDataSource
    private var img_food:ImageView?=null
    private var btnCart:CounterFab?=null
    private var btnRating:FloatingActionButton?=null
    private var food_name:TextView?=null
    private var food_description:TextView?=null
    private var food_price:TextView?=null
    private var number_button:ElegantNumberButton?=null
    private var ratingBar:RatingBar?=null
    private var btnShowComment:Button?=null

    private var waitingDialog:android.app.AlertDialog?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foodDetailViewModel =
            ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_food_detail, container, false)
        initView(root)
        val textView: TextView = root.findViewById(R.id.food_name)
        foodDetailViewModel.getMutableLiveDataFood().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })

        foodDetailViewModel.getMutableLiveDataComment().observe(viewLifecycleOwner, Observer {
            submitRatingToFirebase(it)
        })

        return root
    }

    private fun submitRatingToFirebase(commentModel: CommentModel?) {
        waitingDialog!!.show()
        FirebaseDatabase.getInstance()
            .getReference(Common.COMMENT_REF)
            .child(Common.foodSelected!!.id!!)
            .push()
            .setValue(commentModel)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    addRatingToFood(commentModel!!.ratingValue.toDouble())
                }
                waitingDialog!!.dismiss()
            }
    }

    private fun addRatingToFood(ratingValue: Double) {
        FirebaseDatabase.getInstance()
            .getReference(Common.CATEGORY_REF) // Select category
            .child(Common.categorySelected!!.menu_id!!) // Select menu in category
            .child("foods") // Select foods array
            .child(Common.foodSelected!!.key!!) //
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    waitingDialog!!.dismiss()
                    Toast.makeText(context!!, "" +p0.message,Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        val foodModel = dataSnapshot.getValue(FoodModel::class.java)
                        foodModel!!.key = Common.foodSelected!!.key

                        val sumRating = foodModel.ratingValue!!.toDouble() + ratingValue
                        val ratingCount = foodModel.ratingCount+1
                        val result = sumRating/ratingCount
                        val updateData = HashMap<String,Any>()
                        updateData["ratingValue"] = result
                        updateData["ratingCount"] = ratingCount

                        foodModel.ratingCount = ratingCount
                        foodModel.ratingValue = result

                        dataSnapshot.ref
                            .updateChildren(updateData)
                            .addOnCompleteListener{task ->
                                waitingDialog!!.dismiss()
                                if(task.isSuccessful){
                                    Common.foodSelected = foodModel
                                    foodDetailViewModel!!.setFoodModel(foodModel)
                                    Toast.makeText(context!!, "Thank you" ,Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else waitingDialog!!.dismiss()
                }
            })
    }


    private fun displayInfo(it: FoodModel) {
        Glide.with(requireContext()).load(it!!.image).into(img_food!!)
        food_name!!.text = StringBuilder(it!!.name!!)
        food_description!!.text = StringBuilder(it!!.description!!)
        food_price!!.text = StringBuilder(it!!.price!!.toString() + " VND")

        ratingBar!!.rating = it!!.ratingValue.toFloat()
    }

    private fun initView(root: View?) {
        cartDataSource = LocalCartDataSource(CartDatabase.getInstance(context!!).cartDAO())
        waitingDialog = SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()

        btnCart = root!!.findViewById(R.id.btnCart) as CounterFab
        img_food = root!!.findViewById(R.id.img_food) as ImageView
        btnRating = root!!.findViewById(R.id.btnRating) as FloatingActionButton
        food_name = root!!.findViewById(R.id.food_name) as TextView
        food_description = root!!.findViewById(R.id.food_description) as TextView
        food_price = root!!.findViewById(R.id.food_price) as TextView
        number_button = root!!.findViewById(R.id.number_button) as ElegantNumberButton
        ratingBar = root!!.findViewById(R.id.ratingBar) as RatingBar
        btnShowComment = root!!.findViewById(R.id.btnShowComment) as Button

        // Event
        btnRating!!.setOnClickListener{
            showDialogRating()
        }

        btnCart!!.setOnClickListener{
            var cartItem= CartItem()
            cartItem.uid=Common.currentUser!!.uid
            //cartItem.userPhone=Common.currentUser!!.phone
            cartItem.foodId=Common.foodSelected!!.id!!
            cartItem.foodName=Common.foodSelected!!.name!!
            cartItem.foodImage=Common.foodSelected!!.image!!
            cartItem.foodPrice=Common.foodSelected!!.price.toDouble()
            cartItem.foodQuantity= number_button!!.number.toInt()

            cartDataSource.getItemWithAllOptionsInCart(Common.currentUser!!.uid!!,
                cartItem.foodId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CartItem> {
                    override fun onSuccess(cartItemFromDB: CartItem) {
                        if(cartItemFromDB.equals(cartItem))
                        {
                            //Neu co gio hang trong database thi ta update
                            cartItemFromDB.foodQuantity = cartItem.foodQuantity

                            cartDataSource.updateCart(cartItemFromDB)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : SingleObserver<Int> {
                                    override fun onSuccess(t: Int) {
                                        Toast.makeText(context, "Update Cart Success", Toast.LENGTH_SHORT).show()
                                        EventBus.getDefault().postSticky(CountCartEvent(true))
                                    }

                                    override fun onSubscribe(d: Disposable) {
                                    }

                                    override fun onError(e: Throwable) {
                                        Toast.makeText(context, "[Update Cart]" + e.message, Toast.LENGTH_SHORT).show()
                                    }

                                })
                        }
                        else
                        {
                            //Neu ko co trong database thi insert
                            compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Toast.makeText(context, "Add to cart success", Toast.LENGTH_SHORT).show()
                                    //Thông báo cho HomeActivity update CounterFab
                                    EventBus.getDefault().postSticky(CountCartEvent(true))
                                },{
                                        t:Throwable? -> Toast.makeText(context, "[Insert cart]" +t!!.message, Toast.LENGTH_SHORT).show()
                                }))

                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        if(e.message!!.contains("empty"))
                        {
                            compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Toast.makeText(context, "Add to cart success", Toast.LENGTH_SHORT).show()
                                    //Thông báo cho HomeActivity update CounterFab
                                    EventBus.getDefault().postSticky(CountCartEvent(true))
                                },{
                                        t:Throwable? -> Toast.makeText(context, "[Insert cart]" +t!!.message, Toast.LENGTH_SHORT).show()
                                }))
                        }
                        else Toast.makeText(context, "[CART ERROR]" + e.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    private fun showDialogRating() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Rating Food")
        builder.setMessage("Please fill in your information")

        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_rating_comment, null)

        val ratingBar = itemView.findViewById<RatingBar>(R.id.rating_bar)
        val editComment = itemView.findViewById<EditText>(R.id.edit_comment)

        builder.setView(itemView)
        builder.setNegativeButton("CANCEL") {dialogInterface, i ->  dialogInterface.dismiss()}
        builder.setPositiveButton("DONE") {dialogInterface, i ->
            val commentModel = CommentModel()
            commentModel.name = com.example.bkfoodcourt.Common.Common.currentUser!!.name
            commentModel.uid = com.example.bkfoodcourt.Common.Common.currentUser!!.uid
            commentModel.comment = editComment.text.toString()
            commentModel.ratingValue = ratingBar.rating
            val serverTimeStamp = HashMap<String, Any>()
            serverTimeStamp["timeStamp"] = ServerValue.TIMESTAMP
            commentModel.commentTimeStamp = serverTimeStamp

            foodDetailViewModel!!.setCommentModel(commentModel)
        }
        val dialog = builder.create()
        dialog.show()
    }
}