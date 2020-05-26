package com.linkensky.ornet.presentation.selfcheck

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.linkensky.ornet.Const
import com.linkensky.ornet.data.model.BaseResponse
import com.orhanobut.hawk.Hawk

data class SelfcheckState(
    val phone: String? = Hawk.get<String>(Const.PHONE, null),
    val name: String? = Hawk.get<String>(Const.NAME, null),
    val page: Int = 1,

    val hasFever: Boolean = false,
    val hasFlu: Boolean = false,
    val hasCough: Boolean = false,
    val hasBreathProblem: Boolean = false,
    val hasSoreThroat: Boolean = false,
    val inInfectedCountry: Boolean = false,
    val inInfectedCity: Boolean = false,
    val directContact: Boolean = false,
    val responseStoreTest: Async<BaseResponse> = Uninitialized
) : MvRxState