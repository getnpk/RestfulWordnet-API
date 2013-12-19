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

* **Example**
<blockquote>
curl -X GET -H "Accept: application/xml" -H "Content-type: text/plain" http://localhost:8080/RestfulWordnet/api/wordnet/all/education

<wordPackage>
<synonyms>education</synonyms>
<synonyms>instruction</synonyms>
<synonyms>teaching</synonyms>
<synonyms>pedagogy</synonyms>
<synonyms>didactics</synonyms>
<synonyms>educational activity</synonyms>
<hypernyms>activity</hypernyms>
<hyponyms>coeducation</hyponyms>
<hyponyms>continuing education</hyponyms>
<hyponyms>course</hyponyms>
<hyponyms>course of study</hyponyms>
<hyponyms>course of instruction</hyponyms>
<hyponyms>class</hyponyms>
<hyponyms>elementary education</hyponyms>
<hyponyms>extension</hyponyms>
<hyponyms>extension service</hyponyms>
<hyponyms>university extension</hyponyms>
<hyponyms>extracurricular activity</hyponyms>
<hyponyms>higher education</hyponyms>
<hyponyms>secondary education</hyponyms>
<hyponyms>team teaching</hyponyms>
<hyponyms>work-study program</hyponyms>
</wordPackage>
</blockquote>
