import React from 'react';
import './App.css';
import './assets/css/styles.css';

import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import Home from './pages/Home';
import AddProduct from './pages/AddProduct';
import Products from './pages/tableProduct';

function App() {
  return (
    <Router>
      <div className='App'>
        <nav className='ctn_menu'>
          <ul className='Menu'>
            <li>
              <Link to="/">Trang chủ</Link>
            </li>
            <li>
              <Link to="/add">Thêm</Link>
            </li>
            <li>
              <Link to="/product">Quản lí sản phẩm</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/add" element={<AddProduct />} />
          <Route path="/product" element={<Products/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
