import React, { useContext, useState, useEffect, useCallback } from 'react'
import Axios from 'axios'
import { useParams, Link } from 'react-router-dom'
import AppGlobalsContext from './AppGlobalsContext'
import { url } from './constants'


const CustomerDevices = props => {
    const { id: customerId } = useParams()
    const { token } = useContext(AppGlobalsContext)
    const [devices, setDevices] = useState([])
    const [deviceName, setDeviceName] = useState('')
    const [error, setError] = useState('')

    const getDevices = useCallback(
        () => Axios.get(`${url}/api/customer/${customerId}/devices?page=0&pageSize=100`, {
            headers: { 'X-Authorization': `Bearer ${token}` }
        })
            .then(({ data }) => {
                setDevices(data.data)
            })
            .catch(err => {
                console.error(err)
                setError(err)
            }),
        [customerId, token]
    )

    useEffect(() => {
        getDevices()
    }, [getDevices])

    const handleFormSubmit = (e) => {
        e.preventDefault()
        Axios.post(`${url}/api/device`, { name: deviceName, type: 'default' }, { headers: { 'X-Authorization': `Bearer ${token}` } })
            .then(({ data }) => {
                Axios.post(`${url}/api/customer/${customerId}/device/${data.id.id}`, {}, { headers: { 'X-Authorization': `Bearer ${token}` } })
                    .then(() => getDevices())
                    .catch(err => {
                        console.error(err)
                        setError(err)
                    })
            })
            .catch(err => {
                console.error(err)
                setError(err)
            })
    }

    return <div>
        <h1>Customer Devices</h1>
        <ul>
            {devices.map(device =>
                <li key={device.id.id}>
                    <Link to={`/device/${device.id.id}`}>{device.name}</Link>
                </li>
            )}
        </ul>
        <form onSubmit={handleFormSubmit}>
            <label>Device Name</label>
            <input type='text' required name='device_name' placeholder='Device Name' value={deviceName} onChange={e => setDeviceName(e.target.value)} />
            <br />
            <input type='submit' value='Save' />
        </form>
        {error && <h2>{error?.response?.data?.message || 'Error happened'}</h2>}
    </div>
}

export default CustomerDevices
