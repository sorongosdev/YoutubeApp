package com.example.youtubeapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter: VideoAdapter

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        videoAdapter = VideoAdapter(context = this) { videoItem ->
            // start: collapse, end: expand
            binding.motionLayout.setTransition(R.id.collapse, R.id.expand)
            binding.motionLayout.transitionToEnd()

            play(videoItem)
        }

        initMotionLayout()
        initVideoRecyclerView()

        initControlbutton()

        binding.hideButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.hide)
            //hide가 됐을 때는 영상 일시정지
            player?.pause()
        }
    }

    private fun initControlbutton() {
        binding.controlButton.setOnClickListener {
            // player가 null일 경우 아무것도 하지 않음
            player?.let { // null이 아닐때 처리
                if (it.isPlaying) {
                    it.pause()
                } else {
                    it.play()
                }
            }
        }
    }

    private fun initVideoRecyclerView() {
        binding.videoListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }

        val videoList = readData("videos.json", VideoList::class.java) ?: VideoList(emptyList())
        videoAdapter.submitList(videoList.videos)
    }

    private fun initMotionLayout() {
        binding.motionLayout.targetView = binding.videoPlayerContainer
        // jumpToState는 지원않는 것으로 보임. transitionToState로 대체
        binding.motionLayout.transitionToState(R.id.hide)

        binding.motionLayout.setTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                //뷰가 바뀌고 있는 상태에서 컨트롤러를 끔.
                binding.playerView.useController = false
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                //확장 상태면 컨트롤러를 띄움
                binding.playerView.useController = (currentId == R.id.expand)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }

    private fun play(videoItem: VideoItem) {
        player?.setMediaItem(MediaItem.fromUri(Uri.parse(videoItem.videoUrl)))
        player?.prepare()
        player?.play()

        binding.videoTitleTextView.text = videoItem.title
    }

    private fun initExoPlayer() {
        player = ExoPlayer.Builder(this).build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer
                binding.playerView.useController = false

                exoPlayer.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)

                        if (isPlaying) {
                            binding.controlButton.setImageResource(R.drawable.ic_pause)
                        } else {
                            binding.controlButton.setImageResource(R.drawable.ic_play)
                        }
                    }
                })
            }
    }

    override fun onStart() {
        super.onStart()
        if (player == null) {
            initExoPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initExoPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}