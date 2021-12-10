package pw.yumc.MiaoLog4j2Fix;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 喵式脚本
 *
 * @author 喵♂呜
 * @since 2016年8月29日 上午7:50:39
 */
public class MiaoLog4j2FixBukkit extends JavaPlugin implements Listener {
    @Override
    public void onLoad() {
        new MiaoLog4j2Fix();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().contains("${jndi:")) {
            event.setCancelled(true);
        }
    }
}
