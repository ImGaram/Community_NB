package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.base.DomainBaseResponse
import com.example.domain.model.freeboard.DomainBaseFreeBoardResponse
import com.example.domain.model.freeboard.comment.DomainGetCommentResponse
import com.example.domain.model.freeboard.getpostall.DomainGetAllFreeBoardResponse
import com.example.domain.model.user.DomainLoginResponse
import com.example.domain.model.user.DomainSignInResponse
import com.example.domain.model.user.DomainUserInfoResponse
import com.example.domain.usecase.freeboard.addpost.AddPostUseCase
import com.example.domain.usecase.freeboard.comment.*
import com.example.domain.usecase.freeboard.delete.DeletePostUseCase
import com.example.domain.usecase.freeboard.getpost.GetPostUseCase
import com.example.domain.usecase.freeboard.getpostall.GetPostAllUseCase
import com.example.domain.usecase.freeboard.modify.ModifyPostUseCase
import com.example.domain.usecase.user.delete.DeleteUserUseCase
import com.example.domain.usecase.user.login.LoginUseCase
import com.example.domain.usecase.user.revision.RevisionUseCase
import com.example.domain.usecase.user.signin.SignInUseCase
import com.example.domain.usecase.user.userinfo.UserInfoUseCase
import com.example.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class NbViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val loginUseCase: LoginUseCase,
    private val userInfoUseCase: UserInfoUseCase,
    private val revisionUseCase: RevisionUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val getPostAllUseCase: GetPostAllUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val getCommentUseCase: GetCommentUseCase,
    private val modifyCommentUseCase: ModifyCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val suggestPostUseCase: SuggestPostUseCase,
    private val getSuggestPostUseCase: GetSuggestPostUseCase
): BaseViewModel() {
    // 회원가입
    private val _signInApiCallResult = MutableLiveData<DomainSignInResponse>()
    val signInApiCallResult: LiveData<DomainSignInResponse> = _signInApiCallResult
    fun signInLogic(name: String, email: String, password: String) {
        viewModelScope.launch{
            signInUseCase(name, email, password, viewModelScope) {
                _signInApiCallResult.value = it
                Log.d("SUCCESS", "_signInApiCallResult.value: ${_signInApiCallResult.value}")
            }
        }
    }

    // 로그인
    private val _loginApiCallResult = MutableLiveData<DomainLoginResponse>()
    val loginApiCallResult: LiveData<DomainLoginResponse> = _loginApiCallResult
    fun loginLogic(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password, viewModelScope) {
                _loginApiCallResult.value = it
                Log.d("SUCCESS", "_loginApiCallResult.value: ${_loginApiCallResult.value}")
            }
        }
    }

    // 유저 정보
    private val _userInfoApiCallResult = MutableLiveData<DomainUserInfoResponse>()
    val userInfoApiCallResult: LiveData<DomainUserInfoResponse> = _userInfoApiCallResult
    fun getUserLogic(id: Int) {
        viewModelScope.launch {
            userInfoUseCase(id, viewModelScope) {
                _userInfoApiCallResult.value = it
                Log.d("SUCCESS", "_userInfoApiCallResult.value: ${_userInfoApiCallResult.value}")
            }
        }
    }

    // 유저 수정
   private val _userRevisionApiCallResult = MutableLiveData<DomainBaseResponse>()
    val userRevisionApiCallResult: LiveData<DomainBaseResponse> = _userRevisionApiCallResult
    fun revisionLogic(id: Int, name: String, email: String, password: String) {
        viewModelScope.launch {
            revisionUseCase(id, name, email, password, viewModelScope) {
                _userRevisionApiCallResult.value = it
                Log.d("SUCCESS", "_userRevisionApiCallResult.value: ${_userRevisionApiCallResult.value}")
            }
        }
    }

    // 유저 삭제
    private val _deleteUserApiCallResult = MutableLiveData<ResponseBody>()
    val deleteUserApiCallResult: LiveData<ResponseBody> = _deleteUserApiCallResult
    fun deleteUserLogic(pk: Int) {
        viewModelScope.launch {
            deleteUserUseCase(pk, viewModelScope) {
                _deleteUserApiCallResult.value = it
            }
        }
    }

    // free board

    // 게시물 추가
    private val _addPostApiCallResult = MutableLiveData<DomainBaseFreeBoardResponse>()
    val addPostApiCallResult: LiveData<DomainBaseFreeBoardResponse> = _addPostApiCallResult
    fun createPostLogic(
        title: String, content:String, img1:String, img2:String,
        img3:String, img4:String, img5:String, createUser: Int
    ) {
        viewModelScope.launch {
            addPostUseCase(title, content, img1, img2, img3, img4, img5, createUser, viewModelScope) {
                _addPostApiCallResult.value = it
            }
        }
    }

    // 게시글 정보 모두 가져오기
    private val _getPostAllApiCallResult = MutableLiveData<List<DomainGetAllFreeBoardResponse>>()
    val getPostAllApiCallResult: LiveData<List<DomainGetAllFreeBoardResponse>> = _getPostAllApiCallResult
    fun getPostLogic() {
        viewModelScope.launch {
            getPostAllUseCase(viewModelScope) {
                _getPostAllApiCallResult.value = it
            }
        }
    }

    // 게시글 정보 하나만 가져오기
    private val _getPostSingleApiCallResult = MutableLiveData<DomainBaseFreeBoardResponse>()
    val getPostSingleApiCallResult: LiveData<DomainBaseFreeBoardResponse> get() = _getPostSingleApiCallResult
    fun getPostSingleLogic(id: Int) {
        viewModelScope.launch {
            getPostUseCase(id, viewModelScope) {
                _getPostSingleApiCallResult.value = it
            }
        }
    }

    // 게시글 수정
    private val _modifyPostApiCallResult = MutableLiveData<DomainBaseFreeBoardResponse>()
    val modifyPostApiCallResult: LiveData<DomainBaseFreeBoardResponse> = _modifyPostApiCallResult
    fun modifyPostLogic(
        title: String, content:String, img1:String, img2:String,
        img3:String, img4:String, img5:String, createUser: Int, id: Int
    ) {
        viewModelScope.launch {
            modifyPostUseCase(title, content, img1, img2, img3, img4, img5, createUser, id, viewModelScope) {
                _modifyPostApiCallResult.value = it
            }
        }
    }

    // 게시글 삭제
    private val _deletePostApiCallResult = MutableLiveData<Int>()
    val deletePostApiCallResult: LiveData<Int> = _deletePostApiCallResult
    fun deletePostLogic(pk: Int) {
        viewModelScope.launch {
            deletePostUseCase(pk, viewModelScope) {
                _deletePostApiCallResult.value = it
            }
        }
    }

    // 댓글 추가
    private val _addCommentApiCallResult = MutableLiveData<Int>()
    val addCommentApiCallResult: LiveData<Int> = _addCommentApiCallResult
    fun addCommentLogic(context: String, createUserId: Int, postId: Int) {
        viewModelScope.launch {
            addCommentUseCase(context, createUserId, postId, viewModelScope) {
                _addCommentApiCallResult.value = it
            }
        }
    }

    // 게시들 댓글 불러오기
    private val _getCommentApiCallResult = MutableLiveData<List<DomainGetCommentResponse>>()
    val getCommentApiCallResult: LiveData<List<DomainGetCommentResponse>> = _getCommentApiCallResult
    fun getCommentLogic(postId: Int) {
        viewModelScope.launch {
            getCommentUseCase(postId, viewModelScope) {
                _getCommentApiCallResult.value = it
            }
        }
    }

    // 댓글 수정
    private val _modifyCommentApiCallResult = MutableLiveData<Int>()
    val modifyCommentApiCallResult: LiveData<Int> = _modifyCommentApiCallResult
    fun modifyCommentLogic(
        context: String, createUserId: Int, postId: Int, pk: Int
    ) {
        viewModelScope.launch {
            modifyCommentUseCase(context, createUserId, postId, pk, viewModelScope) {
                _modifyCommentApiCallResult.value = it
            }
        }
    }

    // 댓글 삭제
    private val _deleteCommentApiCallResult = MutableLiveData<Int>()
    val deleteCommentApiCallResult: LiveData<Int> = _deleteCommentApiCallResult
    fun deleteCommentLogic(postId: Int) {
        viewModelScope.launch {
            deleteCommentUseCase(postId, viewModelScope) {
                _deleteCommentApiCallResult.value = it
            }
        }
    }

    // 추천
    private val _suggestSuggestPostApiCallResult = MutableLiveData<Int>()
    val suggestPostApiCallResult: LiveData<Int> = _suggestSuggestPostApiCallResult
    fun suggestLogic(user: Int, board: Int) {
        viewModelScope.launch {
            suggestPostUseCase(user, board, viewModelScope) {
                _suggestSuggestPostApiCallResult.value = it
            }
        }
    }

    // 추천 받아오기
    private val _getSuggestPostApiCallResult = MutableLiveData<Int>()
    val getSuggestPostApiCallResult: LiveData<Int> = _getSuggestPostApiCallResult
    fun getSuggestLogic(board: Int) {
        viewModelScope.launch {
            getSuggestPostUseCase(board, viewModelScope) {
                _getSuggestPostApiCallResult.value = it
            }
        }
    }
}