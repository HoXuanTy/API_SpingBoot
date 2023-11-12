import React, { useState, useEffect } from 'react';
import Product from '../models/productModel';
import productApi from '../api/productApi';
import EditProduct from './EditProduct';

function Products() {
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = () => {
    productApi.getProductList()
      .then((data) => setProducts(data))
      .catch(error => console.error('Có lỗi xảy ra khi lấy danh sách sản phẩm: ', error));
  };

  const handleEdit = (product: Product) => {
    setSelectedProduct(product);
  };

  const handleEditSave = (editedProduct: Product) => {
    productApi.updateProduct(editedProduct)
      .then(() => {
        console.log('Sản phẩm đã được sửa thành công.');
        fetchProducts(); // Cập nhật danh sách sau khi sửa
      })
      .catch(error => console.error('Có lỗi xảy ra khi sửa sản phẩm: ', error));
  };

  const handleDelete = (productId: number) => {
    productApi.deleteProduct(productId)
      .then(() => {
        console.log('Sản phẩm đã được xóa thành công.');
        fetchProducts(); 
      })
      .catch(error => console.error('Có lỗi xảy ra khi xóa sản phẩm: ', error));
  };

  return (
    <div>
      <h1>Danh sách sản phẩm</h1>
      <table>
        <thead>
          <tr>
            <th>Hình ảnh</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.id}>
              <td><img src={product.image} alt={product.productName}/></td>
              <td>{product.productName}</td>
              <td>{product.price}</td>
              <td>
                <button onClick={() => handleEdit(product)}>Sửa</button>
                <button onClick={() => handleDelete(product.id)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {selectedProduct && (
        <EditProduct
          product={selectedProduct}
          onClose={() => setSelectedProduct(null)}
          onSave={handleEditSave}
        />
      )}
    </div>


  );
}

export default Products;
