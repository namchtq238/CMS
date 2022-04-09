#Lưu ý
#9/4/2022
1. Update API vào sheet này https://docs.google.com/spreadsheets/d/1VWjNgyHX6ioMF47lFDC2OQ7VAGMAr-CBOJfSZznyhoQ/edit#gid=0
2. Sử dụng GET để fetch dữ liệu
3. Sử dụng POST để thay đổi dữ liệu, export file, download, import, ....
4. Join bảng thì dùng Converter, không gọi trực tiếp từ bảng khác hoặc trả thẳng về entity
5. Những hàm nào có ảnh hưởng dữ liệu với nhau thì đặt trong cùng 1 transaction
6. Ko chạy forEach lưu từng phần tử một, sử dụng saveAll() - Vì nó có saveInBatch()