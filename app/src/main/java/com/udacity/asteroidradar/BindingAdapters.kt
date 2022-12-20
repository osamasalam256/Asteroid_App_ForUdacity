package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R.drawable.*
import com.udacity.asteroidradar.adapter.MainAdapter
import com.udacity.asteroidradar.domain.AsteroDomain


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(ic_status_normal)
    }
    imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.hazardous_asteroid_image)
    } else {
        imageView.setImageResource(asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
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
@BindingAdapter("asteroidsList")
fun bindRecyclerData(recyclerView: RecyclerView, asteroidList: List<AsteroDomain>?) {
    val adapter = recyclerView.adapter as MainAdapter
    adapter.submitList(asteroidList)
}
@BindingAdapter("imageOfDay")
fun bindImageOfDay(image:ImageView,imageOfDay: PictureOfDay?) {
    imageOfDay?.let {
        if (it.mediaType == "image"){
            Picasso.with(image.context).load(it.url).into(image)
            image.contentDescription = it.title
        }else{
            image.setImageResource(local_image)
            image.contentDescription = image.context.getString(R.string.galaxy_with_asteroid)
        }

    }
}
