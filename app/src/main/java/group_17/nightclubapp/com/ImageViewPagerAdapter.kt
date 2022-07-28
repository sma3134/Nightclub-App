package group_17.nightclubapp.com
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import java.util.*

// help from https://www.geeksforgeeks.org/image-slider-in-android-using-viewpager/
class ImageViewPagerAdapter(
     var context: Context,
     var images: List<Bitmap>
) :
    PagerAdapter() {
    // Layout Inflater
    var mLayoutInflater: LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.img_item, container, false)

        val imageView = itemView.findViewById<View>(R.id.imageViewMap) as ImageView

        imageView.setImageBitmap(images[position])

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        images = images
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}
