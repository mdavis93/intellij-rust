package org.rust.cargo.project.settings

import com.intellij.openapi.externalSystem.settings.ExternalProjectSettings

class CargoProjectSettings : ExternalProjectSettings() {

    protected fun copyTo(receiver: CargoProjectSettings) {
        receiver.cargoHome = cargoHome
        super.copyTo(receiver)
    }

    override fun clone(): ExternalProjectSettings {
        val result = CargoProjectSettings()
        copyTo(result)
        return result
    }

    var cargoHome: String? = null
    var distributionType: Companion.Distribution? = null

    companion object {

        enum class Distribution {
            /**
             * Employ local-distribution of the Cargo
             */
            LOCAL
        }

    }
}
