package com.neeson.common.context.trace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TraceContext {

    private String traceId;

}
