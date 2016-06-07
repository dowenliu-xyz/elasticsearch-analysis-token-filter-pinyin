package xyz.dowenwork.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.settings.IndexSettingsService;
import xyz.dowenwork.lucene.analyzer.PinyinTransformTokenFilter;

/**
 * <p>create at 16-6-6</p>
 * -
 *
 * @author liufl
 * @since 2.3.3.0
 */
public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private final int transformType;
    private final boolean keepOrigin;
    private final int minTermLength;

    @Inject
    public PinyinTokenFilterFactory(Index index,
            IndexSettingsService indexSettingsService, String name, Settings settings) {
        super(index, indexSettingsService.indexSettings(), name, settings);
        int globalTransformType = settings
                .getAsInt("index.analysis.filter.pinyin.transformType", PinyinTransformTokenFilter.TYPE_PINYIN);
        int _transformType = indexSettings()
                .getAsInt("index.analysis.filter.pinyin.transformType", globalTransformType);
        if (_transformType < 0x01 || _transformType > 0x03) {
            logger.warn("pinyin transforming type illegal: {}, use default value {} instead", _transformType,
                    PinyinTransformTokenFilter.TYPE_PINYIN);
            _transformType = PinyinTransformTokenFilter.TYPE_PINYIN;
        }
        this.transformType = _transformType;
        boolean globalKeepOrigin = settings.getAsBoolean("index.analysis.filter.pinyin.keepOrigin", true);
        keepOrigin = indexSettings().getAsBoolean("index.analysis.filter.pinyin.keepOrigin", globalKeepOrigin);
        int globalMinTermLengh = settings.getAsInt("index.analysis.filter.pinyin.minTermLength", 2);
        int _minTermLength = indexSettings().getAsInt("index.analysis.filter.pinyin.minTermLength", globalMinTermLengh);
        if (_minTermLength < 1) {
            logger.warn(
                    "min term length for pinyin transforming illegal: {}, use default value {} instead",
                    _minTermLength, 2);
            _minTermLength = 2;
        }
        this.minTermLength = _minTermLength;
        if (minTermLength > 2) {
            logger.info("2 is recommended for min term length, your value {} may be too large", minTermLength);
        }
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTransformTokenFilter(tokenStream, transformType, minTermLength, keepOrigin);
    }
}
