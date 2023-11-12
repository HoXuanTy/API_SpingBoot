import axios from 'axios';
import Product from '../models/productModel';
import { error } from 'console';
const API_URL = 'http://localhost:8080/api/v1';

const getProductList = () => {
  return axios.get(`${API_URL}/product`)
    .then(response => response.data)
    .catch(error => {
      console.error('Lỗi khi lấy dữ liệu từ API: ', error);
      throw error;
    });
};

const insertProduct = (productData: Omit<Product, 'id'>) => {
  return axios.post(`${API_URL}/product/insert`, productData)
    .then(response => response.data)
    .catch(error => {
      console.error('Lỗi khi thêm sản phẩm: ', error);
      throw error;
    });
};

const updateProduct = (productData: Product) => {
  return axios.put(`${API_URL}/product/${productData.id}`, productData)
    .then(response => response.data)
    .catch(error => {
      console.error('Lỗi khi sửa sản phẩm: ', error);
      throw error;
    });
}

const deleteProduct = (productId: number | undefined) => {
  if(productId !== undefined){
    return axios.delete(`${API_URL}/product/${productId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Lỗi khi xóa sản phẩm:', error);
      throw error;
    });
  }else {
    console.error('Không thể xóa sản phẩm với id không xác định.');
    throw new Error('Không thể xóa sản phẩm với id không xác định.');
  }
}


export default {
  getProductList, insertProduct, updateProduct, deleteProduct
};
