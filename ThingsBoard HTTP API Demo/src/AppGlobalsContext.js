import { createContext } from 'react'

const AppGlobalsContext = createContext({
    token: '',
    loggedIn: false
})

export default AppGlobalsContext
