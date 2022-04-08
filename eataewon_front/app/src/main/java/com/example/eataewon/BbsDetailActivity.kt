package com.example.eataewon

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.eataewon.Adapter.ViewPagerAdapter
import com.example.eataewon.connect.*
import com.example.eataewon.databinding.ActivityBbsDetailBinding
import com.example.eataewon.vPager.Fragment1
import com.example.eataewon.vPager.Fragment2
import com.example.eataewon.vPager.Fragment3
import com.example.eataewon.vPager.Fragment4
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        val writeData = intent.getParcelableExtra<BbsDto>("writeData")
        println("글쓰기하고 넘어온  seq 확인 ${writeData?.seq.toString()}~~~~~~~")

        var getBbsList: BbsDto? = null
        if(writeData!=null){
            getBbsList = BbsDao.getInstance().getBbsList(writeData.seq!!)
            println("getBbsList == writeData")
            println("getBbsList 출력 !!!! ${getBbsList.toString()}")
        }

        //로그인 유저정보
        val prefs = getSharedPreferences("sharedPref", 0)
        val loginUserId = prefs.getString("loginUserId","로그인유저 정보없음")
        val loginUserNickname = prefs.getString("loginUserNickname","로그인유저 정보없음")
        println("${loginUserId}  ${loginUserNickname} ~~~~~~~~~~~~~")

        //툴바 생성_안도현
        val toolbar = binding.bbsdetailToolbar
        setSupportActionBar(toolbar)


        //어댑터에서 싼 짐 푸르기 (메인에서 디테일로 넘어온 데이터)
        val homeData = intent.getParcelableExtra<BbsDto>("clickBbs")
        val updateData = intent.getParcelableExtra<BbsDto>("updateToDetail")
        var data : BbsDto? = null

        if(homeData!=null){
            data = homeData //홈에서 클릭해서 넘어온 값
            println("data == homeData")
        }else if(updateData!=null){
            data = updateData   //수정페이지에서 수정해서 넘어온 값
            println("data == updateData")
        }else if(getBbsList!=null){
            data = getBbsList   //글작성페이지에서 작성 후 넘어온 값
            println("data == getBbsList")
        }

        //      글쓴이랑 로그인유저가 같을때
        if(loginUserId.equals(data?.id)){
            binding.deleteBtn.isVisible = true
            binding.updateBtn.isVisible = true
        }else{
            binding.deleteBtn.isVisible = false
            binding.updateBtn.isVisible = false
        }

        println("data 출력 테스트 : !!!!!!!!! : ${data.toString()}")
        //글쓴이 프로필사진 가져오기
        println("글쓴이 아이디 : ${data?.id}")
        val profilPic = MemberDao.getInstance().getProfilPic(data?.id.toString()!!)
        val picture = data?.picture.toString().split(" ")

        //툴바 타이틀에 넣기_안도현
        toolbar.title=data?.title
        binding.profilPicture.setImageURI(Uri.parse(profilPic!![0].toString()))
        binding.DeBbsUserT.text = data?.nickname
        binding.DeBbsLikePoT.text = "${data?.likecnt.toString()}명 좋아요"
        binding.DeBbsWdateT.text = data?.wdate
        binding.DeTitle.text = data?.title
        binding.DeContent.text = data?.content
        binding.DeHashtag.text = data?.hashtag
        binding.DeShopName.text = data?.shopname
        binding.DeBbsShopLocaT.text = data?.address
        binding.DeBbsShopPhT.text = data?.shopphnum
        binding.DeBbsShopUrlT.text = data?.shopurl


        //사진 뷰페이저로 보여주기
        val vPager2 = findViewById<ViewPager2>(R.id.vPager2)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val frag1 = Fragment1()
        val frag2 = Fragment2()
        val frag3 = Fragment3()
        val frag4 = Fragment4()

        val fragList = listOf<Fragment>(frag1,frag2,frag3,frag4)
        val adapter = ViewPagerAdapter(this)
        adapter.fList = fragList
        vPager2.adapter = adapter

        //인디케이터
        TabLayoutMediator(tabLayout, vPager2) { tab, position ->
            //Some implementation...
        }.attach()

        //등록된 사진 수만큼 보여주기
        if(picture[0]!=null){
            frag1.setImg(picture[0])
        }else if(picture[1]!=null){
            frag2.setImg(picture[1])
        }else if(picture[2]!=null){
            frag3.setImg(picture[2])
        }else if(picture[3]!=null){
            frag4.setImg(picture[3])
        }



        //이미 스크랩을 눌렀던 글인지 확인하는 조건문 필요 (스크랩 눌러놨으면 노란리본으로 표시해주기)
        //이미 좋아요를 눌렀던 글인지 확인하는 조건문 필요 (좋아요 눌러놨으면 하트빨간색으로 표시해주기)
        var id = loginUserId
        var bbsseq = data?.seq

        var sdto = ScrapDto(id,bbsseq,null)
        var checkScrap = BbsDao.getInstance().checkUserScrap(sdto)

        if(checkScrap==true){
            binding.HeartBtn.isSelected = true  //좋아요 누른상태
        }else{
            binding.HeartBtn.isSelected = false  //좋아요 안누른상태
        }

        var ldto = LikeDto(id,bbsseq,null)
        var checkLike = BbsDao.getInstance().checkUserLike(ldto)

        if(checkLike==true){
            binding.ScrapBtn.isSelected = true  //스크랩 누른상태
        }else{
            binding.ScrapBtn.isSelected = false //스크랩 안누른상태
        }



        binding.deleteBtn.setOnClickListener {
            var likePointDown = BbsDao.getInstance().LikePWriteDown(data?.id!!)
            if(likePointDown==true){
                println("글 삭제로 글쓴이 ${data.id}의 호감도가 50 하락했습니다")
            }else{
                println("글 삭제로 글쓴이 ${data.id}의 호감도 하락에 실패했습니다")
            }
            var delete = BbsDao.getInstance().bbsDelete(data?.seq!!)

            if(delete == true){
                Toast.makeText(this,"글이 삭제되었습니다",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"글삭제를 실패했습니다",Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
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

                //이태원라이크 테이블에 유저값 넣어주기
                val id = loginUserId
                val bbsseq = data?.seq
                val dto = LikeDto(id, bbsseq, null)
                var plusLike = BbsDao.getInstance().insertLike(dto)
                if(plusLike==true){
                    println("Like테이블에 ${data?.seq}번호의 글에 ${loginUserId}님이 좋아요를 눌렀습니다")
                }else{
                    println("Like테이블 반영 실패")
                }

                val checkLikeP = BbsDao.getInstance().LikePHeartUp(data?.id!!)
                if(checkLikeP==true){
                    println("하트버튼 클릭으로 현재글쓴이 ${data.id}의 호감도가 상승했습니다")
                }else{
                    println("하트버튼 클릭으로 현재글쓴이 호감도 상승에 실패했습니다")
                }

            }else{
                binding.HeartBtn.isSelected = false //좋아요 누른거 취소

                //+이태원라이크 테이블에 유저값 삭제하기
                val id = loginUserId
                val bbsseq = data?.seq
                val dto = LikeDto(id, bbsseq, null)
                var deleteLike = BbsDao.getInstance().deleteLike(dto)
                if(deleteLike==true){
                    println("Like테이블에 ${data?.seq}번호의 글에 ${loginUserId}님이 좋아요를 취소했습니다")
                }else{
                    println("Like테이블 반영 실패")
                }

                val checkLikeP = BbsDao.getInstance().LikePHeartDown(data?.id!!)
                if(checkLikeP==true){
                    println("하트버튼 클릭취소로 현재글쓴이 ${data.id}의 호감도가 하락했습니다")
                }else{
                    println("하트버튼 클릭취소로 현재글쓴이 호감도 하락에 실패했습니다")
                }
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

                //이태원스크랩 테이블에 유저값 넣어주기
                val id = loginUserId
                val bbsseq = data?.seq
                val dto = ScrapDto(id, bbsseq, null)
                var plusScrap = BbsDao.getInstance().insertScrap(dto)
                if(plusScrap==true){
                    println("Scrap테이블에 ${data?.seq}번호의 글에 ${loginUserId}님이 스크랩버튼을 눌렀습니다")
                }else{
                    println("Scrap테이블 반영 실패")
                }

                val checkLikeP = BbsDao.getInstance().LikePScrapUp(data?.id!!)
                if(checkLikeP==true){
                    println("스크랩버튼 클릭으로 현재글쓴이 ${data.id}의 호감도가 상승했습니다")
                }else{
                    println("스크랩버튼 클릭으로 현재글쓴이 호감도 상승에 실패했습니다")
                }
            }else{
                binding.ScrapBtn.isSelected = false //스크랩 누른거 취소

                //+이태원스크랩 테이블에 유저값 삭제하기
                val id = loginUserId
                val bbsseq = data?.seq
                val dto = ScrapDto(id, bbsseq, null)
                var deleteScrap = BbsDao.getInstance().deleteScrap(dto)
                if(deleteScrap==true){
                    println("Scrap테이블에 ${data?.seq}번호의 글에 ${loginUserId}님이 스크랩버튼을 취소했습니다")
                }else{
                    println("Scrap테이블 반영 실패")
                }

                val checkLikeP = BbsDao.getInstance().LikePScrapDown(data?.id!!)
                if(checkLikeP==true){
                    println("스크랩버튼 클릭취소로 현재글쓴이 ${data.id}의 호감도가 상승했습니다")
                }else{
                    println("스크랩버튼 클릭취소로 현재글쓴이 호감도 상승에 실패했습니다")
                }
            }
        }

        val naverMapFragment = NaverMapFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.mapContent, naverMapFragment).commit()

        binding.showDetailShop.setOnClickListener {

            binding.mapContent.isVisible = true
            binding.showDetailShopField.isVisible = true

            naverMapFragment.setLocation(data!!.latitude, data!!.longitude)
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
