package com.example.eataewon


import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.databinding.ActivityBbsDetailBinding
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.Content
import com.kakao.sdk.template.Link
import com.kakao.sdk.template.LocationTemplate
import com.kakao.sdk.template.Social

//import com.kakao.sdk.template.model.*

class BbsDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityBbsDetailBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        var seq = 1

//        var data = BbsDao.getInstance().getBbsDetail(seq)

        //툴바 생성_안도현
        val toolbar = binding.bbsdetailToolbar
        setSupportActionBar(toolbar)




        //어댑터에서 싼 짐 푸르기 (메인에서 디테일로 넘어온 데이터)
        val data = intent.getParcelableExtra<BbsDto>("data")

        //툴바 타이틀에 넣기_안도현
        toolbar.title=data?.title
        binding.DeTitle.text = data?.title
        binding.DeContent.text = data?.content
        binding.DeHashtag.text = data?.hashtag
        binding.DeLocation.text = data?.address

//       data에 같이 넘어온 글쓴이 아이디로 유저정보 가져오기 (사진,닉네임,한줄소개, 호감도)
        /*var id = data?.id
        var userData = MemberDao.getInstance().bbsGetUser(id!!)*/

        //이미 좋아요를 눌렀던 글인지 확인하는 조건문 필요 (좋아요 눌러놨으면 하트빨간색으로 표시해주기)
        //이미 스크랩을 눌렀던 글인지 확인하는 조건문 필요 (스크랩 눌러놨으면 노란리본으로 표시해주기)

//        var plusScrap = BbsDao.getInstance().plusBbsScrap(data!!)

        //var testData = listOf<BbsDto>("id",10,"title","content","picture")


//        if(글쓴이랑 로그인유저가 같을때) if조건문 수정필요
        if(true){
            binding.deleteBtn.isVisible = true
            binding.updateBtn.isVisible = true
        }else{
            binding.deleteBtn.isVisible = false
            binding.updateBtn.isVisible = false
        }

        binding.deleteBtn.setOnClickListener {
          /*  var delete = BbsDao.getInstance().bbsdelete(data!!.seq)
            if(delete == true){
                Toast.makeText(this,"글이 삭제되었습니다",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"글삭제를 실패했습니다",Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)*/
        }

        binding.updateBtn.setOnClickListener {
            val i = Intent(this,UpdateBbsActivity::class.java)
            i.putExtra("passUpdate",data)
            startActivity(i)
        }

//      좋아요 버튼 클릭효과
        binding.HeartBtn.setOnClickListener {
            if(binding.HeartBtn.isSelected != true){
                binding.HeartBtn.isSelected = true  //좋아요 누르기
                //+이태원라이크 테이블에 유저값 넣어주기
                //var plusLike = BbsDao.getInstance().plusBbsLike(data!!)
            }else{
                binding.HeartBtn.isSelected = false //좋아요 누른거 취소
                //+이태원라이크 테이블에 유저값 삭제하기
            }
        }

        //툴바아이템 클릭
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.bbsdetail_exitBtn->{

                    Toast.makeText(this,"취소",Toast.LENGTH_SHORT).show()
                    true
                }

                else->false
            }
        }

        //툴바아이템 클릭
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.bbsdetail_exitBtn->{

                    Toast.makeText(this,"취소",Toast.LENGTH_SHORT).show()
                    true
                }

                else->false
            }
        }

//      스크랩 버튼 클릭효과
        binding.ScrapBtn.setOnClickListener {
            if(binding.ScrapBtn.isSelected != true){
                binding.ScrapBtn.isSelected = true  //스크랩 누르기
                //+이태원스크랩 테이블에 유저값 넣어주기
                //  var plusScrap = BbsDao.getInstance().plusBbsScrap()   //스크랩누르면
            }else{
                binding.ScrapBtn.isSelected = false //스크랩 누른거 취소
                //+이태원스크랩 테이블에 유저값 삭제하기
            }
        }

        binding.showDetailShop.setOnClickListener {

            binding.mapContent.isVisible = true
            binding.showDetailShopField.isVisible = true

            val naverMapFragment = NaverMapFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.mapContent, naverMapFragment).commit()

        }


//      카톡 글 공유하기
        binding.floShareBtn.setOnClickListener {

            // 피드 메시지 보내기
            val defaultText = LocationTemplate(
                address = data?.address!!,
                addressTitle = data?.shopname!!,
                content = Content(
                    title = data?.title!!,
                    description = data?.content!!.substring(0,20),
                    imageUrl = "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20220301_56%2F1646125809470g3mRx_JPEG%2Fupload_8734af74cb52984491186a224aa2a66e.jpeg",
                    link = Link(
//                        webUrl = "https://developers.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                social = Social(
                    likeCount = 286,
                    commentCount = 45,
                    sharedCount = 845
                )
            )
            // 사용자 정의 메시지 ID
            //  * 만들기 가이드: https://developers.kakao.com/docs/latest/ko/message/message-template

            // 카카오톡 설치여부 확인
            if (LinkClient.instance.isKakaoLinkAvailable(this)) {
                // 카카오톡으로 카카오링크 공유 가능
                LinkClient.instance.defaultTemplate(this, defaultText) { linkResult, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "카카오링크 보내기 실패", error)
                    }
                    else if (linkResult != null) {
                        Log.d(ContentValues.TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                        startActivity(linkResult.intent)

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w(ContentValues.TAG, "Warning Msg: ${linkResult.warningMsg}")
                        Log.w(ContentValues.TAG, "Argument Msg: ${linkResult.argumentMsg}")
                    }
                }
            } else {
                // 카카오톡 미설치: 웹 공유 사용 권장
                // 웹 공유 예시 코드
                val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultText)

                // CustomTabs으로 웹 브라우저 열기

                // 1. CustomTabs으로 Chrome 브라우저 열기
                try {
                    KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
                } catch(e: UnsupportedOperationException) {
                    // Chrome 브라우저가 없을 때 예외처리
                }

                // 2. CustomTabs으로 디바이스 기본 브라우저 열기
                try {
                    KakaoCustomTabsClient.open(this, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 인터넷 브라우저가 없을 때 예외처리
                }
            }

        }

    }

    //툴바 연결_안도현
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.bbsdetail_toolbar,menu)
        return true
    }
}



