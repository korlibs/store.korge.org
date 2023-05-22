
function delay(ms) {
    return new Promise((resolve, _) => {
        setTimeout(resolve, ms)
    })
}

function htmlspecialchars(text) {
    if (text == null || text == undefined) return null
    const div = document.createElement('div')
    div.innerText = text
    return div.innerHTML
}

let currentAudio = null

function stopSounds() {
    currentAudio?.stop?.()
    currentAudio?.pause?.()
    currentAudio = null
}

function playSound(e, url) {
    const eurl = `${url || e.href}`
    stopSounds()
    if (eurl.endsWith(".mp3") || eurl.endsWith(".wav")) {
        currentAudio = new Audio(eurl)
        currentAudio.play()
    } else {
        const modPlayer = new ScripTracker();
        modPlayer.on(ScripTracker.Events.playerReady, () => {
            modPlayer.play()
        });
        console.log(modPlayer)
        modPlayer.loadModule(eurl);
        currentAudio = modPlayer
    }

    return false
}
function downloadAsset(e, url, unzip, base = 'sfx', outputname = undefined, author = "Unknown") {
    try {
        const filename = `${url}`.split('/').pop()
        const icon = 'about:blank'
        const output = `${base}/${outputname || filename}`
        const html = `<html>
                <head>
                    <style>
                    :root {
                        --bgcolor: #3c3f41ff;
                        --labelcolor: #bbbbbbff;
                    }
                    body {
                        background: var(--bgcolor);
                        color: var(--labelcolor);
                        font: 14px Arial;
                    }
                    p {
                        margin: 0; padding: 0;
                    }
                    </style>
                </head>
                <body>
                    <p><strong>Title:</strong> ${htmlspecialchars(filename)}</p>
                    <p><strong>Output:</strong> <code>${htmlspecialchars(output)}</code></p>
                    <p><strong>Author:</strong> ${htmlspecialchars(author)}</p>
                    <p style="white-space: nowrap"><strong>URL:</strong> ${htmlspecialchars(url)}</p>
                    <div>&nbsp;</div>
                    <!--
                    <img src="${htmlspecialchars(icon)}" style="max-height:calc(100vh - 6em);">
                    -->
                </body>
            </html>`

        window.jcefFunctions.downloadAsset({
            url: url || e.href,
            html: html,
            output: output,
            unzip: unzip || false,
        })
    } catch (e) {
        alert(`${e}`)
        throw e
    }
}
