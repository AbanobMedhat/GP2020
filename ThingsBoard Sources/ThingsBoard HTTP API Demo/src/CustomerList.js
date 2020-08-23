import React, { useContext, useState, useEffect, useCallback } from 'react'
import AppGlobalsContext from './AppGlobalsContext'
import Axios from 'axios'
import { url } from './constants'
import { Link } from 'react-router-dom'

const CustomerList = props => {
    const { token } = useContext(AppGlobalsContext)
    const [customers, setCustomers] = useState([])
    const [customerName, setCustomerName] = useState('')
    const [error, setError] = useState('')

    const getCustomersList = useCallback(
        () => Axios.get(`${url}/api/customers?page=0&pageSize=100`, {
            headers: { 'X-Authorization': `Bearer ${token}` }
        })
            .then(({ data }) => {
                setCustomers(data.data)
            })
            .catch(err => {
                console.error(err)
                setError(err)
            }),
        [token]
    )

    useEffect(() => {
        getCustomersList()
    }, [getCustomersList])

    const handleFormSubmit = (e) => {
        e.preventDefault()
        Axios.post(`${url}/api/customer`, { name: customerName, title: customerName }, { headers: { 'X-Authorization': `Bearer ${token}` } })
            .then(({ data }) => {
                getCustomersList()
                setCustomerName('')
            })
            .catch(err => {
                console.error(err)
                setError(err)
            })
    }

    const handleCustomerDelete = ({ id: { id } }) => {
        Axios.delete(`${url}/api/customer/${id}`, { headers: { 'X-Authorization': `Bearer ${token}` } })
            .then(() => getCustomersList())
            .catch(err => {
                console.error(err)
                setError(err)
            })
    }

    return <div>
        {error && <h2>{error?.response?.data?.message || 'Error happened'}</h2>}
        <h1>Customers list</h1>
        <ul>
            {customers.map(customer =>
                <li key={customer.id.id}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '50%', marginBottom: 10 }}>
                        <Link to={`/customer/${customer.id.id}`}>{customer.name}</Link>
                        <button onClick={() => handleCustomerDelete(customer)}>Delete</button>
                    </div>
                </li>
            )}
        </ul>
        <h3>Add New customer</h3>
        <form onSubmit={handleFormSubmit}>
            <label>Customer Name</label>
            <input type='text' required name='customer_name' placeholder='Customer Name' value={customerName} onChange={e => setCustomerName(e.target.value)} />
            <br />
            <input type='submit' value='Save' />
        </form>
    </div>
}

export default CustomerList
