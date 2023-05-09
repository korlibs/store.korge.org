# KorGE Store

To contribute fork this repository, then execute (changing the URL):

```bash
./addlink.main.kts https://github.com/korlibs/korge-fleks/tree/main/korge-fleks
```

That will create the proper entry, will download the tags and commitIds.

Now you can try the store locally with (you will need to have [`jekyllrb` installed](https://jekyllrb.com/docs/installation/)):

```bash
./start.sh
```

And opening <http://127.0.0.1:4000/> in your browser.

Then you can make a PR for your module/library/assert to be included.
