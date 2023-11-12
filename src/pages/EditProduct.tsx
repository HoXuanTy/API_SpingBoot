import React, { useState } from 'react';
import Product from '../models/productModel';

interface EditProductProps {
    product: Product;
    onClose: () => void;
    onSave: (editedProduct: Product) => void;
  }
  

  const EditProduct: React.FC<EditProductProps> = ({ product, onClose, onSave }) => {
    const [editedProduct, setEditedProduct] = useState<Product>({ ...product });
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setEditedProduct({
      ...editedProduct,
      [name]: value,
    });
  };

  const handleSave = () => {
    onSave(editedProduct);
    onClose();
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={onClose}>&times;</span>
        <h2>Sửa sản phẩm</h2>
        <div>
            <label>Tên sản phẩm:</label>
            <input
            type="text"
            name="productName"
            value={editedProduct.productName}
            onChange={handleChange}
            />
        </div>
        <div>
            <label>Hình ảnh:</label>
            <input
            type="text"
            name="image"
            value={editedProduct.image}
            onChange={handleChange}
            />
        </div>
        <div>
            <label>Giá:</label>
            <input
            type="number"
            name="price"
            value={editedProduct.price}
            onChange={handleChange}
            />
        </div>
        <button onClick={handleSave}>Lưu</button>
      </div>
    </div>
  );
};

export default EditProduct;
