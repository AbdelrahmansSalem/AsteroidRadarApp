package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.MainAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}


@BindingAdapter("imageOfToday")
fun bindImageOfToday(imageView: ImageView,pictureOfDay: PictureOfDay?){

    if(pictureOfDay!=null){
        Picasso.get().
        load(pictureOfDay?.url).
        placeholder(R.drawable.placeholder_picture_of_day).
        error(R.drawable.asteroid_hazardous).
        fit().
        into(imageView)
    }

}

@BindingAdapter("ispotentiallyHazardous")
fun bindPotentiallyHazardous(imageView: ImageView,asteroid: Asteroid){
    if (asteroid.isPotentiallyHazardous){
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    }
    else{
        imageView.setImageResource(R.drawable.ic_status_normal)
    }

}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data:List<Asteroid>?){
    val adapter=recyclerView.adapter as MainAdapter
    adapter.submitList(data)
}