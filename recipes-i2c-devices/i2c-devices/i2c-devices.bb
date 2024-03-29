SUMMARY = "Script to properly configure i2c-devs driver on Raspberry Pi 3 (BCM2837)"
HOMEPAGE = "https://github.com/dchvs/i2c-devs.git"
SECTION = "kernel"
LICENSE = "GPLv2"

PR = "r3"
DEPENDS += "libnl gtest ktf"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "2deb9c60ac57ff88f1bbff2c5adf53395747ad0e"
SRC_URI = " git://github.com/dchvs/i2c-devs.git;branch=lethani"

S = "${WORKDIR}/git"


export KERNEL_SRC="${STAGING_KERNEL_BUILDDIR}"
export KTF_INCLUDE_DIRS="${STAGING_INCDIR}"

inherit pkgconfig cmake module-base kernel-module-split

do_install() {
    install -d ${D}${bindir}
    cp -R ${WORKDIR}/build/user/src/i2c_devs ${D}${bindir}

    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers
    cp -R ${S}/kernel/src/i2c-devs.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers
}

FILES_${PN} = " \
      ${bindir}/i2c_devs \
      /lib/modules/${KERNEL_VERSION}/kernel/drivers/i2c-devs.ko \
"

FILES_${PN}-dev = ""
FILES_${PN}-staticdev = ""
PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = " dev-deps"
RDEPENDS_${PN} = " python ktf"

KERNEL_MODULE_AUTOLOAD += "i2c-devs"
