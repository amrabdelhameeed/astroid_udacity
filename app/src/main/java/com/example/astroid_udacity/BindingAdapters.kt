package com.example.astroid_udacity


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    if (!data.isNullOrEmpty()){
        val adapter = recyclerView.adapter as AsteroidAdapter
        adapter.submitList(data)
    }
}

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
        imageView.contentDescription = imageView.context.getString(
            R.string.potentially_hazardous_asteroid_image
        )
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.contentDescription = imageView.context.getString(
            R.string.not_hazardous_asteroid_image
        )
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

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrBlank().not()) {
        Picasso.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.placeholder_picture_of_day)
            .into(imageView)
    }
}
