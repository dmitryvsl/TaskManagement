import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.taskmanagement.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension>(){
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.configure<BaseAppModuleExtension>{
                configureKotlinAndroid(this)
                applicationVariants.all {
                    sourceSets {
                        getByName(name){
                            this.kotlin.srcDir("build/generated/ksp/$name/kotlin")
                        }
                    }
                }
            }
        }
    }
}