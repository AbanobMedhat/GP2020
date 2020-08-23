import React, { useEffect, useState } from 'react'
import axios from 'axios'
import {
    BrowserRouter as Router,
    Switch,
    Route
} from 'react-router-dom'

import AppGlobalsContext from './AppGlobalsContext'
import CustomerList from './CustomerList'
import { url, authInfo } from './constants'
import CustomerDevices from './CustomerDevices'
import Device from './Device'

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false)
    const [token, setToken] = useState('')
    const [error, setError] = useState('')

    useEffect(() => {
        axios.post(`${url}/api/auth/login`,
            { username: authInfo.username, password: authInfo.password },
            {
                headers: { 'Content-Type': 'application/json' }
            }
        )
            .then(({ data }) => {
                setToken(data.token)
                setIsLoggedIn(true)
            })
            .catch(err => {
                console.error(err)
                setError(err)
            })

    }, [])

    if (error) return <h1>{error?.response?.data?.message || 'Error happened'}</h1>

    if (isLoggedIn === false) return <h1>Loading!!!!</h1>
    return <Router>
        <AppGlobalsContext.Provider value={{ token, loggedIn: isLoggedIn }}>
            {error && <h2>{error.response.data.message}</h2>}
            <Switch>
                <Route path='/' exact>
                    <CustomerList />
                </Route>
                <Route path='/customer/:id'>
                    <CustomerDevices />
                </Route>
                <Route path='/device/:id'>
                    <Device />
                </Route>
            </Switch>
        </AppGlobalsContext.Provider>
    </Router>

}

export default App
