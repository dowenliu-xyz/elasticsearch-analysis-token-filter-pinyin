拼音转换tokenFilter ES支持
==========
elasticsearch.yml:

    index:
        analysis:
            filter:
                transformType: 1
                keepOrigin: true
                minTermLength: 2

index settings:

    curl -XPUT http://localhost:9200/pinyin -d'
    {
      "settings": {
        "index": {
          "analysis": {
            "filter": {
              "transformType": 1,
              "keepOrigin": true,
              "minTermLength": 2
            }
          }
        }
      }
    }
    '

选项
* transformType: 1 仅输出全拼（默认）， 2 仅输出简拼， 3 同时输出全拼和简拼
* keepOrigin: 是否保留原Token，默认为true
* minTermLength: 进行拼音转换需要最少中文长度，默认为2
