To use above library just follow steps

1.build.gradle(Project:test) add "maven { url 'https://jitpack.io' }"

example:-

allprojects { repositories { google() jcenter() maven { url 'https://jitpack.io' }

}
}

2.add dependency implementation 'com.github.kaleaakash96:ElegantNumberBtnlib:v1.0'
