const express = require('express')
const bodyParser = require('body-parser');
require('body-parser-xml')(bodyParser);
const app = express()
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json())
app.use(bodyParser.xml());
const port = 3000

const IRCC_CODES = []
IRCC_CODES["AAAAAgAAAJcAAABPAw=="] = 'UP'
IRCC_CODES["AAAAAgAAAJcAAABQAw=="] = 'DOWN'
IRCC_CODES["AAAAAgAAAJcAAABNAw=="] = 'LEFT'
IRCC_CODES["AAAAAgAAAJcAAABOAw=="] = 'RIGHT'
IRCC_CODES["AAAAAgAAAJcAAABKAw=="] = 'CENTER'
IRCC_CODES["AAAAAQAAAAEAAABgAw=="] = 'HOME'
IRCC_CODES["AAAAAgAAAJcAAAAjAw=="] = 'RETURN'


let tvState = {
    volume: 0,
    power: true
}


app.get('/', (req, res) => {
    res.send(tvState)
})


app.post('/sony/system', (req, res) => {
    const body = req.body
    const method = body.method

    if (method == 'getPowerStatus') res.send(getPowerStatus())
    else if (method == 'setPowerStatus') res.send(setPowerStatus(body.params[0]))

    logState()
})

app.post('/sony/audio', (req, res) => {
    const body = req.body
    const method = body.method
    // {"id": 20, "method": "getVolumeInformation", "version": "1.0", "params": [] }
    if (method == 'getVolumeInformation') res.send(getVolumeInformation())
    else if (method == 'setAudioVolume') res.send(setAudioVolume(body.params[0]))

    logState()
})

app.post('/sony/IRCC', (req, res) => {
    const code = req.body['s:Envelope']['s:Body'][0]['u:X_SendIRCC'][0]['IRCCCode'][0]
    console.log('got code', code, '->', IRCC_CODES[code])
    res.sendStatus(200)
})


function getPowerStatus() {
    if (tvState.power) return {"result": [{"status": "active"}], "id": 50}
    else return {"result": [{"status": "standby"}], "id": 50}

}

function setPowerStatus(json) {
    const newPowerStatus = json.status
    if (newPowerStatus) tvState.power = true
    else tvState.power = false
    return '';
}

function setAudioVolume(json) {
    // {"method":"setAudioVolume","version":"1.0","id":1,"params":[{"target":"speaker","volume":"$value"}]}
    tvState.volume = json.volume
    return ''
}

function getVolumeInformation() {
    if (tvState.power) return {
        "result": [[{
            "target": "speaker",
            "volume": tvState.volume,
            "mute": false,
            "maxVolume": 100,
            "minVolume": 0
        }]], "id": 20
    }
    else return {"error": [40005, "Display Is Turned off"], "id": 20}
}


function logState() {
    console.log("tvState", tvState)
}


app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})
