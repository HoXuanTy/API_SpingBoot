import React, { useState, useEffect } from 'react';
import productApi from '../api/productApi';
import Product from '../models/productModel';


function Home() {
  const [products, setProducts] = useState<Product[]>([]);

 useEffect(()=>{
    productApi.getProductList()
    .then(data =>setProducts(data))
    .catch(error => console.error('xảy ra lỗi:', error));
 },[])

  return (
    <div>
    <h1>Danh sách sản phẩm</h1>
    <div className="product-list">
      {products.map((product) => (
        <div className="product-card">
          <img src={product.image} alt={product.productName} />
          <div className="product-info">
            <h2>{product.productName}</h2>
            <p>Giá: {product.price}</p>
          </div>
        </div>
      ))}
    </div>
  </div>
);
}

export default Home;
