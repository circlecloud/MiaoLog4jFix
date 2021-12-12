package pw.yumc.MiaoLog4j2Fix;

import lombok.SneakyThrows;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.AppenderControlArraySet;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.core.pattern.LiteralPatternConverter;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import pw.yumc.MiaoLog4j2Fix.reflect.Reflect;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 喵式脚本
 *
 * @author 喵♂呜
 * @since 2016年8月29日 上午7:50:39
 */
public class MiaoLog4j2Fix {
    @SneakyThrows
    public MiaoLog4j2Fix(Object console) {
        Logger logger;
        try {
            logger = Reflect.on(console).field("LOGGER").get();
        } catch (final Throwable e) {
            Field loggerField = console.getClass().getDeclaredFields()[0];
            logger = (Logger) loggerField.get(console);
        }
        fix(logger);
    }

    public void fix(Logger logger) {
        try {
            logger = logger.getParent();
            log("根日志: %s", logger);
            removeJndi(logger);
            log("补丁已加载完成...");
        } catch (final Throwable e) {
            e.printStackTrace();
            log("========== [MiaoLog4j2Fix] ==========");
            log("!!!!!! 补丁加载失败 !!!!!! 请手动修复服务端!");
            log("========== [MiaoLog4j2Fix] ==========");
        }
    }

    public void removeJndi(Logger logger) {
        AppenderControlArraySet set = Reflect.on(logger).field("privateConfig").field("loggerConfig").field("appenders").get();
        AppenderControl[] appenderControls = set.get();
        for (AppenderControl appenderControl : appenderControls) {
            Layout<? extends Serializable> layout = appenderControl.getAppender().getLayout();
            if (layout != null) {
                Object serializer = Reflect.on(layout).field("eventSerializer").get();
                String serializerName = serializer.getClass().getName();
                if ("org.apache.logging.log4j.core.layout.PatternLayout$PatternSerializer".equals(serializerName)) {
                    removeFormatterLookups(Reflect.on(serializer).field("formatters").get());
                } else if ("org.apache.logging.log4j.core.layout.PatternLayout$PatternSelectorSerializer".equals(serializerName)) {
                    Object patternSelector = Reflect.on(serializer).field("patternSelector").get();
                    removeFormatterLookups(Reflect.on(patternSelector).field("defaultFormatters").get());
                    List<Object> formatters = Reflect.on(patternSelector).field("formatters").get();
                    formatters.forEach(o -> {
                        removeFormatterLookups(Reflect.on(o).field("formatters").get());
                    });
                } else {
                    log("格式化类: %s 序列化类: %s 未找到格式化数组 请联系开发者兼容!!!", appenderControl.getClass().getName(), serializerName);
                }
            }
        }
    }

    public void removeFormatterLookups(PatternFormatter[] formatters) {
        for (PatternFormatter formatter : formatters) {
            LogEventPatternConverter converter = formatter.getConverter();
            if (converter instanceof LiteralPatternConverter) {
                AbstractConfiguration config = Reflect.on(converter).field("config").get();
                StrLookup strLookup = config.getStrSubstitutor().getVariableResolver();
                Map<String, Object> lookups = Reflect.on(strLookup).field("lookups").get();
                lookups.put("jndi", new NoopLookup());
            }
        }
    }

    public void log(String format, Object... args) {
        System.out.println("[MiaoLog4j2Fix] " + String.format(format, args));
    }

    public static class NoopLookup extends AbstractLookup {
        @Override
        public String lookup(LogEvent logEvent, String s) {
            return null;
        }
    }
}
