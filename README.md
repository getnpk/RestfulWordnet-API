RestfulWordnet-API
==================

Custom Wordnet offering as RESTful API

* **Wordnet Offering**
  - synonyms
  - hypernyms
  - hyponyms

* **Supported Formats**
  - application/json
  - application/xml

* **Usage Example**
* Set Wordnet database path in resources/config.properties

<blockquote>
curl -X GET -H "Accept: application/json" -H "Content-type: text/plain" http://localhost:8080/RestfulWordnet/api/wordnet/all/education
</blockquote>
