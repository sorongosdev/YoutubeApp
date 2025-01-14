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
import com.example.youtubeapp.player.PlayerHeader
import com.example.youtubeapp.player.PlayerVideoAdapter
import com.example.youtubeapp.player.transform

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var playerVideoAdapter: PlayerVideoAdapter

    private var player: ExoPlayer? = null

    private val videoList: VideoList by lazy {
        readData("videos.json", VideoList::class.java) ?: VideoList(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initMotionLayout()
        initVideoRecyclerView()
        initPlayerVideoRecyclerView()

        initControlButton()
        initHideButton()

        videoAdapter.submitList(videoList.videos)
    }

    private fun initHideButton() {
        binding.hideButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.hide)
            //hide가 됐을 때는 영상 일시정지
            player?.pause()
        }
    }

    private fun initControlButton() {
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
        videoAdapter = VideoAdapter(context = this) { videoItem ->
            binding.motionLayout.setTransition(R.id.collapse, R.id.expand)
            binding.motionLayout.transitionToEnd()

            val headerModel = PlayerHeader(
                id = "H${videoItem.id}",
                title = videoItem.title,
                videoItem.channelName,
                videoItem.viewCount,
                videoItem.dateText,
                videoItem.channelThumb
            )

            // 현재 플레이어에서 보여주고 있는 아이템은 헤더로, 그 아래부터는 나머지 리스트를 보여줌
            val list = listOf(headerModel) + videoList.videos.filter {
                it.id != videoItem.id
            }.map { it.transform() }
            playerVideoAdapter.submitList(list)

            play(videoItem.videoUrl, videoItem.title)
        }

        binding.videoListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }
    }

    private fun initMotionLayout() {
        playerVideoAdapter = PlayerVideoAdapter(context = this) { playerVideo ->
            play(playerVideo.videoUrl, playerVideo.title)
        }

        binding.motionLayout.targetView = binding.videoPlayerContainer
        // jumpToState는 지원않는 것으로 보임. transitionToState로 대체
        binding.motionLayout.transitionToState(R.id.hide)

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
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

    private fun initPlayerVideoRecyclerView() {
        playerVideoAdapter = PlayerVideoAdapter(context = this) { playerVideo ->
            play(playerVideo.videoUrl, playerVideo.title)

            val headerModel = PlayerHeader(
                id = "H${playerVideo.id}",
                title = playerVideo.title,
                playerVideo.channelName,
                playerVideo.viewCount,
                playerVideo.dateText,
                playerVideo.channelThumb
            )

            val list = listOf(headerModel) + videoList.videos.filter { it.id != playerVideo.id }
                .map { it.transform() }
            playerVideoAdapter.submitList(list) {
                binding.playerRecyclerView.scrollToPosition(0)
            }

        }

        binding.playerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerVideoAdapter
            itemAnimator = null
        }
    }

    private fun play(videoUrl: String, videoTitle: String) {
        player?.setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
        player?.prepare()
        player?.play()

        binding.videoTitleTextView.text = videoTitle
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