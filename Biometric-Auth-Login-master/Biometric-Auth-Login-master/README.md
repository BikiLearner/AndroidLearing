# Sample Biometric Authentication
Biometric Authentication verification to any Android application</br>


<p><a href="https://github.com/thulasiram-sarathy/Biometric-Auth-Login/blob/master/screenshots/biometric.png" target="_blank"><img src="https://github.com/thulasiram-sarathy/Biometric-Auth-Login/blob/master/screenshots/biometric.png" width="250" style="max-width:100%;"></a></p>
</br></br></br>



<h2>Integration steps</h2>
<b>Gradle Dependecy</b></br>

```gradle
dependencies {
       implementation 'com.github.thulasiram-sarathy:Fingerprint-Library:1.1'
}
```

```gradle
allprojects {
    repositories {
        ....
        maven { url 'https://jitpack.io' }
    }
}
```

<h2>How to add in your project</h2>

#Kotlin
```
            mBiometricManager = BiometricManager.BiometricBuilder(this@MainActivity)
                .setTitle("Title")
                .setScreen("Dialog position") // center || fullscreen || bottom
                .setTitleBarText(false) // set true if titlebar recquired
                .setSubtitle("Sub Title")
                .setDescription("Description")
                .setNegativeButtonText(Cancel)
                .build()

            mBiometricManager!!.authenticate(this@MainActivity)
```

#Java
```
            new BiometricManager.BiometricBuilder(MainActivity.this)
                .setTitle("Title")
                .setScreen("center") // center || fullscreen || bottom
                .setTitleBarText(false) // set true if titlebar recquired
                .setSubtitle("Sub Title")
                .setDescription("Description")
                .setNegativeButtonText(Cancel)
                .build()
                .authenticate(biometricCallback);
```

#Attributes
```
  SetScreen("center") ||  SetScreen("bottom") || SetScreen("fullscreen")  -> For changing position of fingerprint dialog
  
  setTitleBarText(false) -> false - Hide titlebar || true - show titlebar
  
```




