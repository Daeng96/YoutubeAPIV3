package app.com.youtubeapiv3.ui.adapters

import YoutubeDataModel
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.com.youtubeapiv3.databinding.ItemVideoLayoutBinding
import app.com.youtubeapiv3.interfaces.OnItemClickListener
import com.squareup.picasso.Picasso

class VideoAdapter(val context: Context,val listener: OnItemClickListener): RecyclerView.Adapter<VideoAdapter.YoutubeVideoHolder>() {
    var videoList: List<YoutubeDataModel>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    inner class YoutubeVideoHolder(val binding: ItemVideoLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<YoutubeDataModel>() {
        override fun areItemsTheSame(oldItem: YoutubeDataModel, newItem: YoutubeDataModel): Boolean {
            return oldItem.id.videoId == newItem.id.videoId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: YoutubeDataModel, newItem: YoutubeDataModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeVideoHolder {
        return YoutubeVideoHolder(
            ItemVideoLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: YoutubeVideoHolder, position: Int) {
        holder.binding.apply {
            val video = videoList[position]
            textViewTitle.setText(video.snippet.title)
            textViewDes.setText(video.snippet.description)
            textViewDate.setText(video.snippet.publishedAt)
            holder.binding.root.setOnClickListener {
                listener.onItemClick(video)
            }
            Picasso.with(context).load(video.snippet.thumbnails.high.url).into(ImageThumb)
        }
    }

    override fun getItemCount() = videoList.size
}