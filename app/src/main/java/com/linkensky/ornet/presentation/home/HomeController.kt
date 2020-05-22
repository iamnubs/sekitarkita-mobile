package com.linkensky.ornet.presentation.home

import androidx.navigation.findNavController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.carousel
import com.linkensky.ornet.*
import com.linkensky.ornet.presentation.base.BaseController

class HomeController : BaseController() {
    override fun buildModels() {
        topBar {
            id("home-top-bar")
            text("SekitarKita")
            onInfoClick { view ->
                view.findNavController().navigate(R.id.action_homeFragment_to_macAddressFragment)
            }
        }

        greeting {
            id("greeting")
            greeting("Selamat Malam")
            name("Ghuniyu")
            zoneInfo("Anda sedang berada di Zona Hijau Covid-19")
        }

        header {
            id("confirm")
            text("Data Indonesia Terkini")
        }

        subHeader {
            id("confirm_info")
            text("Data ini disediakan oleh kawalcorona.com")
        }

        stats {
            id("stats")
            recovered("4,838")
            positive("20,165")
            death("1,278")
        }

        selfCheck {
            id("self-check")
        }

        header {
            id("partner")
            text("Kerja Sama")
        }

        subHeader {
            id("confirm_info")
            text("Saat ini Kami Bekerjasama dengan")
        }

        carousel {
            padding(Carousel.Padding.dp(16, 16, 16, 16, 8))
            id("card-info-carousel")
            models(
                1.rangeTo(5).map {
                    GeneralCardBindingModel_()
                        .id("partner-$it")
                        .name("Pemprov Gorontalo")
                        .image(R.drawable.logo5)
                }
            )
            numViewsToShowOnScreen(1.5f)
        }
    }
}