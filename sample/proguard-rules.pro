#
# This ProGuard configuration file illustrates how to process Android
# applications.
# Usage:
#     java -jar proguard.jar @android.pro
#
# If you're using the Android SDK, the Ant release build and Eclipse export
# already take care of the proper settings. You only need to enable ProGuard
# by commenting in the corresponding line in project.properties. You can still
# add project-specific configuration in proguard-project.txt.
#
# This configuration file is for custom, stand-alone builds.

-ignorewarnings

# Specify the input jars, output jars, and library jars.
# Note that ProGuard works with Java bytecode (.class),
# before the dex compiler converts it into Dalvik code (.dex).

#-injars  bin/classes
#-injars  libs
#-outjars bin/classes-processed.jar

#-libraryjars /usr/local/opt/android-sdk/platforms/android-23/android.jar
#-libraryjars /usr/local/android-sdk/add-ons/google_apis-7_r01/libs/maps.jar
# ...

# Save the obfuscation mapping to a file, so you can de-obfuscate any stack
# traces later on.

-printmapping build/mapping.txt

# You can print out the seeds that are matching the keep options below.

#-printseeds bin/classes-processed.seeds

# Reduce the size of the output some more.

-repackageclasses ''
-allowaccessmodification

# Keep a fixed source file attribute and all line number tables to get line
# numbers in the stack traces.
# You can comment this out if you're not interested in stack traces.

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Preserve annotated Javascript interface methods.

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Your application may contain more items that need to be preserved;
# typically classes that are dynamically created using Class.forName:

# -keep public class mypackage.MyClass
# -keep public interface mypackage.MyInterface
# -keep public class * implements mypackage.MyInterface

# If you wish, you can let the optimization step remove Android logging calls.

-assumenosideeffects class android.util.Log {
    public static int d(...);
}
#    public static boolean isLoggable(java.lang.String, int);
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int e(...);

# Kotlin
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
# If you want to get rid of null checks at runtime you may use the following rule:
#-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
#    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
#}

# Retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**

# Picasso
-dontwarn com.squareup.okhttp.**

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature

# Keep your model classes
-keep class cn.nekocode.kotgo.sample.data.DO.** { *; }
