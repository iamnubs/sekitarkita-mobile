package com.linkensky.ornet.presentation.home

import android.view.Gravity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.linkensky.ornet.*
import com.linkensky.ornet.presentation.base.MvRxEpoxyController
import com.linkensky.ornet.presentation.base.item.Frame
import com.linkensky.ornet.presentation.base.item.LayoutOption
import com.linkensky.ornet.presentation.base.item.component.LottieLoading
import com.linkensky.ornet.presentation.base.item.component.MaterialButtonView
import com.linkensky.ornet.presentation.base.item.component.ViewText
import com.linkensky.ornet.presentation.base.item.keyValue
import com.linkensky.ornet.utils.addModel
import com.linkensky.ornet.utils.dp
import com.linkensky.ornet.utils.resString
import com.linkensky.ornet.utils.sp
import com.orhanobut.hawk.Hawk
import java.util.*

data class Zone(
    val name: String,
    val info: String,
    val radar: String
)

@BindingAdapter("android:url")
fun setImageURL(view: ImageView, url: String?) {
    Glide
        .with(view)
        .load(url)
        .into(view)
}
class HomeController(private val viewModel: HomeViewModel) : MvRxEpoxyController() {

    override fun buildModels() = withState(viewModel) { state ->
        val zones = hashMapOf(
            'r' to Zone("Merah", "Anda Sedang di Zona Merah Covid-19", "lottie/radar-red.json"),
            'g' to Zone("Hijau", "Anda Sedang di Zona Hijau Covid-19", "lottie/radar-green.json"),
            'y' to Zone("Kuning", "Anda Sedang di Zona Kuning Covid-19", "lottie/radar-red.json")
        )
        val k = state.zone

        topBar {
            id("home-top-bar")
            text("SekitarKita")
            zone(k)
            onInfoClick { view ->
                view.findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
            }
        }

        var greeting = "Selamat Pagi"
        val calendar = Calendar.getInstance()
        when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                greeting = "Selamat Pagi"
            }
            in 12..15 -> {
                greeting = "Selamat Siang"
            }
            in 16..20 -> {
                greeting = "Selamat Sore"
            }
            in 21..23 -> {
                greeting = "Selamat Maklam"
            }
        }

        greeting {
            id("greeting")
            greeting(greeting)
            name(Hawk.get(Const.NAME, R.string.anonym.resString()))
            zoneInfo(zones[k]?.info)
            zone(k)
            onSelfcheck { view ->
                view.findNavController().navigate(R.id.action_homeFragment_to_selfcheckFragment)
            }
        }

        locationInfo {
            id("location")
            address(state.location)
        }

        header {
            id("confirm")
            text("Data Indonesia Terkini")
        }

        if (Hawk.get(Const.AREA_NAME, "") == Const.AREA_GORONTALO) {
            subHeader {
                id("confirm_info_gorontalo")
                text("Data ini disediakan oleh gorontaloprov.go.id")
            }

            when (val response = state.gorontaloStatistics) {
                is Loading -> {
                    gtoStats {
                        id("stats-loading-gorontalo")
                        isLoading(true)
                    }
                }

                is Success -> {
                    val data = response().data
                    gtoStats {
                        id("stats-gorontalo")
                        isLoading(false)
                        odp(data[0].statuses[0].orangs_count.toString())
                        pdp(data[1].statuses[0].orangs_count.toString())
                        positive(data[2].statuses[0].orangs_count.toString())

                    }
                }

                is Fail -> {
                    addModel(
                        "stas-fail-text-gorontalo",
                        ViewText.Model(
                            text = R.string.something_went_wrong.resString(),
                            gravity = Gravity.CENTER,
                            layout = LayoutOption(margin = Frame(8.dp, 32.dp, 8.dp, 0.dp)),
                            textSize = 13f.sp
                        )
                    )
                    addModel(
                        "stas-fail-gorontalo",
                        MaterialButtonView.Model(
                            text = R.string.refresh.resString(),
                            clickListener = keyValue { _ -> viewModel.getIndonesiaStatistics() },
                            allCaps = false,
                            layout = LayoutOption(margin = Frame(8.dp, 8.dp, 8.dp, 32.dp))
                        )
                    )
                }
            }
        } else {
            subHeader {
                id("confirm_info")
                text("Data ini disediakan oleh kawalcorona.com")
            }

            when (val response = state.indonesiaStatistics) {
                is Loading -> {
                    stats {
                        id("stats")
                        isLoading(true)
                    }
                }

                is Success -> {
                    val data = response().first()
                    stats {
                        id("stats")
                        isLoading(false)
                        recovered(data.sembuh)
                        positive(data.positif)
                        death(data.meninggal)
                    }
                }

                is Fail -> {
                    addModel(
                        "stas-fail-text",
                        ViewText.Model(
                            text = R.string.something_went_wrong.resString(),
                            gravity = Gravity.CENTER,
                            layout = LayoutOption(margin = Frame(8.dp, 32.dp, 8.dp, 0.dp)),
                            textSize = 13f.sp
                        )
                    )
                    addModel(
                        "stas-fail",
                        MaterialButtonView.Model(
                            text = R.string.refresh.resString(),
                            clickListener = keyValue { _ -> viewModel.getIndonesiaStatistics() },
                            allCaps = false,
                            layout = LayoutOption(margin = Frame(8.dp, 8.dp, 8.dp, 32.dp))
                        )
                    )
                }
            }
        }

        header {
            id("partner")
            text("Kerja Sama")
        }

        subHeader {
            id("confirm_info")
            text("Saat ini Kami Bekerjasama dengan")
        }

        when (val banners = state.banners) {
            is Loading -> addModel(
                "banner-loading",
                LottieLoading(
                    layout = LayoutOption(
                        margin = Frame(
                            right = 8.dp,
                            left = 8.dp,
                            top = 80.dp,
                            bottom = 0.dp
                        )
                    )
                )
            )

            is Success -> carousel {
                padding(Carousel.Padding.dp(16, 16, 16, 16, 8))
                id("card-info-carousel")
                models(
                    banners().mapIndexed { i, banner ->
                        PartnerCardBindingModel_()
                            .id("partner-$i")
                            .image("${BuildConfig.APP_IMAGE_URL}${banner.logo}")
                    }
                )
                numViewsToShowOnScreen(1.5f)
            }
        }
    }
}