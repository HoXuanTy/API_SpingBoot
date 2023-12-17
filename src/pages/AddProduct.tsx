import React, { useState } from 'react';
import productApi from '../api/productApi';
import Product from '../models/productModel';

function AddProduct() {
  const [product, setProduct] = useState<Product>({
    id: 0,
    productName: '',
    image: '',
    price: 0,
  });
  const [file, setFile] = useState<File | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;

    if (type === 'file') {
      const selectedFile = e.target.files && e.target.files[0];
      setFile(selectedFile);
    } else {
      setProduct({
        ...product,
        [name]: value,
      });
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!product.productName || !product.price || !file) {
      alert('Vui lòng nhập đầy đủ thông tin sản phẩm và chọn ảnh.');
      return;
    }

    productApi.uploadFile(file)
      .then((imageUrl) => {
        setProduct({
          ...product,
          image: imageUrl,
        });

        return productApi.insertProduct(product);
      })
      .then(() => {
        console.log('Sản phẩm đã được thêm thành công.');
        alert('Thêm thành công');
      })
      .catch((error) => console.error('Có lỗi xảy ra khi thêm sản phẩm: ', error));
  };



  return (
    <div>
      <h1>Thêm sản phẩm</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Tên sản phẩm:</label>
          <input
            type="text"
            name="productName"
            value={product.productName}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Hình ảnh:</label>
          <input
            type="file"
            name="image"
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Giá:</label>
          <input
            type="number"
            name="price"
            value={product.price}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Thêm sản phẩm</button>
      </form>
    </div>
  );
}

export default AddProduct;
