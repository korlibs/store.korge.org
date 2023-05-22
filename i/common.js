
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
