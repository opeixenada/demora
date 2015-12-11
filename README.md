# demora

Finagle HTTP server with the following characteristics:
- path: `GET: /foo/bar`
- returns 200 response code after 1 second of "work"
- avg response time ~ \<concurrent requests\> / 2

## Running

```
git clone https://github.com/opeixenada/demora.git
cd demora
sbt run
```
