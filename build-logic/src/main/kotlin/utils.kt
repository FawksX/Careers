import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

fun ShadowJar.reloc(pckg: String) {
    relocate(pckg, "dev.fawks.careers.libs.$pckg")
}