#Lưu ý
#9/4/2022
1. Update API vào sheet này https://docs.google.com/spreadsheets/d/1VWjNgyHX6ioMF47lFDC2OQ7VAGMAr-CBOJfSZznyhoQ/edit#gid=0
2. Sử dụng GET để fetch dữ liệu
3. Sử dụng POST để thay đổi dữ liệu, export file, download, import, ....
4. Join bảng thì dùng Converter, không gọi trực tiếp từ bảng khác hoặc trả thẳng về entity
5. Những hàm nào có ảnh hưởng dữ liệu với nhau thì đặt trong cùng 1 transaction
6. Ko chạy forEach lưu từng phần tử một, sử dụng saveAll() - Vì nó có saveInBatch()


#17/04/2022
1. Xóa xong relationship giữa các bảng trong entity và database
2. Lưu ý sáng mai thêm Transaction và xóa các bảng có quan hệ với nhau gồm
   1. Xóa Idea -> Xóa document + Like + Comment của Idea đó
   2. Xóa Department -> Xóa Xóa Idea của department đó
   3. Xóa Staff -> Xóa comment + Idea của nó
   4. Xóa QA -> Xóa department của nó
   5. Xóa Category: chỉ thằng nào is_active = 0 mới được xóa (Tức là category đấy đang không được gắn với bất kỳ một idea nào)
3. Pull code về từ master, resolve conflict trước khi push code