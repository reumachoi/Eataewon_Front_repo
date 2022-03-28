package com.example.eataewon;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto

import com.example.eataewon.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity(),View.OnClickListener{

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

///////
    // firebase 인증을 위한 변수
    var auth : FirebaseAuth? = null

        // 구글 로그인 연동에 필요한 변수
        var googleSignInClient : GoogleSignInClient? = null
        var GOOGLE_LOGIN_CODE = 9001
        val database = Firebase.database

///////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //디테일 테스트 버튼 (최아름)
        binding.testBtn.setOnClickListener {
            val i = Intent(this, BbsDetailActivity::class.java)
            startActivity(i)
        }
        //글작성 테스트 버튼 (최아름)
        binding.button3.setOnClickListener {
            val i = Intent(this, KakaoActivity::class.java)
            startActivity(i)
        }

     /*   //백엔드 통신 확인용
        var result = MemberDao.getInstance().test()
        binding.loginID.setText(result.toString())
        binding.textView9.text = result.toString()
        println(result.toString()+"test 확인")*/

        val loginBtn = findViewById<Button>(R.id.login_Btn)
        val signUpBtn = findViewById<Button>(R.id.signUpAtivity_Btn)


        loginBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)


    // 로그인 페이지가 첫화면임 (activity_main.xml의 레이아웃 사용)
    setContentView(R.layout.activity_main)

    // firebaseauth를 사용하기 위한 인스턴스 get
    auth = FirebaseAuth.getInstance()

    // xml에서 구글 로그인 버튼 코드 가져오기
    var google_sign_in_button = findViewById<SignInButton>(R.id.google_sign_in_button)

    // 구글 로그인 버튼 클릭 시 이벤트 : googleLogin function 실행
    google_sign_in_button.setOnClickListener {
        Log.d("googleLogin", "!!!googleLogin!!")
        googleLogin()
    }

    // 구글 로그인을 위해 구성되어야 하는 코드 (Id, Email request)
    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("763712107163-f31ivndnv69i1852nf40i3vf0ot0q1rv.apps.googleusercontent.com")
        .requestEmail()
        .build()
    googleSignInClient = GoogleSignIn.getClient(this, gso)




    // 카카오 로그인 정보 확인
    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
        if (error != null) {
            Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
        }
        else if (tokenInfo != null) {
            Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }


//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)


    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

        Log.d("callback", "!!111!!!!!!!!!!!!!!!!!!!!!callback")
        if (error != null) {
            Log.d("callback", "!!111!!!!!!!!!!!!!!!!!!!!!callback"+ error.toString() )
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (token != null) {
            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    //카카오 로그인 버튼
    val kakao_login_button = findViewById<ImageButton>(R.id.kakao_login_button) // 로그인 버튼

    kakao_login_button.setOnClickListener {
        Log.d("kakao_login_button", "!!!!!!!!!!!!!!!!!!!!!!!kakao_login_button")
        if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
            Log.d("kakao_login_button", "!!111!!!!!!!!!!!!!!!!!!!!!kakao_login_button")
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            Log.d("kakao_login_button", "!!!222!!!!!!!!!!!!!!!!!!!!kakao_login_button")
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    }

    override fun onClick(view: View?) {
        val loginID = findViewById<TextView>(R.id.loginID)
        val loginPW = findViewById<TextView>(R.id.loginPw)

        when (view?.id) {

            R.id.login_Btn -> {
                val id = loginID.text.toString()
                val pwd = loginPW.text.toString()
                val dto = MemberDto(id, "", pwd, "", "",0, 0,"")

                //백엔드 접속후 id pw값을 찾아 login값에 dto값 넣기
                val login = MemberDao.getInstance().login(dto)

                if (login != null) {
                    Toast.makeText(this, "환영합니다. ${login.id}님", Toast.LENGTH_SHORT).show()

                    //안도현(로그인 후 홈엑티비티로 넘어가면서 intent.put으로 login값 넘기기)
                    val intent = Intent(this,HomeActivity::class.java)
                    intent.putExtra("user",login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.signUpAtivity_Btn -> startActivity(Intent(this, SignActivity::class.java))


        }
    }

    fun googleLogin() {
        Log.d("googleLogin", "@@@@@@@@@@@@googleLogin")
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    } // googleLogin

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("requestCode", "!@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!@@@requestCode"+requestCode)
        if(requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result != null) {
                if(result.isSuccess) {
                    var account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
            }
        } //if
    } // onActivityResult

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    // 로그인 성공 시
                    Toast.makeText(this,  "success", Toast.LENGTH_LONG).show()
                    startActivity(Intent (this, StudyRecommendActivity::class.java))
                } else {
                    // 로그인 실패 시
                    Toast.makeText(this,  task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    } //firebaseAuthWithGoogle


}

