package xyz.dowenwork.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.AnalysisModule;

/**
 * <p>create at 16-6-6</p>
 *
 * @author liufl
 * @since 2.3.3.0
 */
public class PinyinAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {
    @Override
    public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
        tokenFiltersBindings.processTokenFilter("pinyin", PinyinTokenFilterFactory.class);
    }
}
