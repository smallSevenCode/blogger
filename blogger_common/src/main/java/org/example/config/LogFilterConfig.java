package org.example.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.utils.BlockingQueueUtil;
import org.slf4j.utils.MdcUtil;

/**
 * 日志收集过滤器
 *
 * @author: 张鹏
 * @date: 2022/6/26 1:49
 **/
@Slf4j
public class LogFilterConfig extends Filter<ILoggingEvent> {

    /**
     * 重新decide方法,获取到日志信息
     *
     * @param iLoggingEvent
     * @return
     */
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        String message = StrUtil.strBuilder()
                .append("<pre>")
                .append(DateUtil.date(iLoggingEvent.getTimeStamp()))
                .append("\t")
                .append(MdcUtil.get())
                .append("\t")
                .append(iLoggingEvent.getLevel().levelStr)
                .append("\t")
                .append(iLoggingEvent.getLoggerName())
                .append("\t")
                .append(iLoggingEvent.getThreadName())
                .append("\t")
                .append(iLoggingEvent.getFormattedMessage())
                .append("<pre>")
                .toString();
        // 日志信息发送到队列中
        BlockingQueueUtil.push(message);
        return FilterReply.ACCEPT;
    }
}
