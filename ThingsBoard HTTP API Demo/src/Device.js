import React, { useContext, useState, useEffect, useCallback } from 'react'
import Axios from 'axios'
import { useParams } from 'react-router-dom'
import AppGlobalsContext from './AppGlobalsContext'
import { url } from './constants'

const veryHistoricDateTs = '1479735870785'
const veryFuturisticDateTs = '1679735871858'


const Device = props => {
    const { id: deviceId } = useParams()
    const { token } = useContext(AppGlobalsContext)
    const [values, setValues] = useState([])
    const [value, setValue] = useState(0)
    const [error, setError] = useState('')

    const getData = useCallback(
        () => Axios.get(`${url}/api/plugins/telemetry/DEVICE/${deviceId}/values/timeseries?keys=temperature&startTs=${veryHistoricDateTs}&endTs=${veryFuturisticDateTs}`, {
            headers: { 'X-Authorization': `Bearer ${token}` }
        })
            .then(({ data }) => {
                setValues(data.temperature || [])
            })
            .catch(err => {
                console.error(err)
                setError(err)
            }),
        [deviceId, token]
    )

    useEffect(() => {
        getData()
    }, [getData])

    const handleFormSubmit = (e) => {
        e.preventDefault()
        Axios.post(`${url}/api/plugins/telemetry/DEVICE/${deviceId}/timeseries/temperature`, { temperature: value }, { headers: { 'X-Authorization': `Bearer ${token}` } })
            .then(({ data }) => {
                getData()
            })
            .catch(err => {
                console.error(err)
                setError(err)
            })
    }

    return <div>
        <h1>Device</h1>
        <ul>
            {values.map(v =>
                <li key={v.ts}>
                    {v.value}
                </li>
            )}
        </ul>
        <h3>Enter Value</h3>
        <form onSubmit={handleFormSubmit}>
            <label>Value</label>
            <input type='number' required name='device_value' placeholder='Device Value' value={value} onChange={e => setValue(e.target.value)} />
            <br />
            <input type='submit' value='Save' />
        </form>
        {error && <h2>{error?.response?.data?.message || 'Error happened'}</h2>}
    </div>
}

export default Device
