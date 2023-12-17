import React, { useState, useEffect } from 'react';
import productApi from '../api/productApi';
import Product from '../models/productModel';
import EditProduct from './EditProduct';

function Home() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await productApi.getProductList();
        setProducts(data);
      } catch (error) {
        setError(`Xảy ra lỗi: ${error}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);


  const handleEdit = (product: Product) => {
    setSelectedProduct(product);
  };


  if (loading) {
    return <h2>Loading...</h2>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (products.length ===0){
    return <p>Không có sản phẩm</p>
  }

  return (
    <div>
      <h1>Danh sách sản phẩm</h1>
      <div className="product-list" >
        {products.map((product) => (
          <div key={product.id} className="product-card" onClick={() => handleEdit(product)}>
            <img src={product.image} alt={product.productName} />
            <div className="product-info">
              <h2>{product.productName}</h2>
              <p>Giá: {product.price}</p>
            </div>
          </div>
        ))}
      </div>
      {selectedProduct && (
        <EditProduct 
        product={selectedProduct}
        onClose={() => setSelectedProduct(null)}
        onSave={()=>{console.log('ok')}}
        />
      )}
    </div>
    
  );
}

export default Home;

