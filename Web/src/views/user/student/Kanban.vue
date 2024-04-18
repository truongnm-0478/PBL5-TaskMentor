<template>
    <div class="kanban-board">
        <div v-for="(list, index) in lists" :key="index" class="kanban-list" :data-index="index">
            <div class="list-header">
                <input v-model="list.title" @blur="saveListTitle(index)" class="list-title-input" />
                <span class="delete-list" @click="deleteList(index)">✖</span>
            </div>
            <div class="drop-zone" @drop="onDrop($event, index)" @dragover.prevent @dragenter.prevent>
                <a-card
                    :key="item.id"
                    v-for="(item, itemIndex) in list.items"
                    :title="item.title"
                    :draggable="true"
                    @dragstart="startDrag($event, item)"
                    @dragover="dragOver(index, item)"
                >
                {{ item.title }}
                </a-card>
                <a-card v-if="isAddingItem">
                    <input type="text" v-model="newItemTitle" placeholder="Enter title"/>
                    <a-button @click="addItem(index)">Add</a-button>
                    <a-button @click="cancelAddItem">Cancel</a-button>
                </a-card>
                <a-button v-else @click="showAddItemForm">Add a Card</a-button>
            </div>
        </div>
        <a-button @click="addList">Add a List</a-button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            lists: [
                {
                    title: 'To Do',
                    items: [
                        { id: 0, title: 'Task 1' },
                        { id: 1, title: 'Task 2' }
                    ]
                },
                {
                    title: 'Doing',
                    items: [
                        { id: 2, title: 'Task 3' },
                        { id: 3, title: 'Task 4' }
                    ]
                },
                {
                    title: 'Done',
                    items: [
                        { id: 4, title: 'Task 5' },
                        { id: 5, title: 'Task 6' }
                    ]
                }
            ],
            newItemTitle: '',
            newTitle: '',
            isAddingItem: false,
            dropIndex: -1 // Biến lưu trữ vị trí chèn
        };
    },
    methods: {
        startDrag(evt, item) {
            evt.dataTransfer.dropEffect = 'move';
            evt.dataTransfer.effectAllowed = 'move';
            evt.dataTransfer.setData('itemID', item.id);
            evt.dataTransfer.setData(
                'fromIndex',
                parseInt(evt.target.closest('.kanban-list').getAttribute('data-index'))
            );
        },
        onDrop(evt, toIndex) {
            const fromIndex = parseInt(evt.dataTransfer.getData('fromIndex'));
            const itemID = parseInt(evt.dataTransfer.getData('itemID'));
            const toList = this.lists[toIndex];

            // Tìm danh sách từ index của toIndex
            const fromList = this.lists[fromIndex];

            if (fromList && toList) {
                // Kiểm tra nếu đang thả vào cùng một danh sách
                if (fromList === toList) {
                    const itemIndex = fromList.items.findIndex(item => item.id === itemID);
                    const item = fromList.items.splice(itemIndex, 1)[0];

                    // Tính toán vị trí chèn dựa trên vị trí chuột trong sự kiện drop
                    const dropIndex = Array.from(evt.target.closest('.drop-zone').childNodes).indexOf(evt.target);

                    // Chèn phần tử vào vị trí mới
                    toList.items.splice(dropIndex, 0, item);

                    console.log(`Moved item with ID ${itemID} within list ${toIndex}`);
                } else { // Nếu đang thả vào một danh sách khác
                    const itemIndex = fromList.items.findIndex(item => item.id === itemID);
                    const item = fromList.items.splice(itemIndex, 1)[0];

                    // Tính toán vị trí chèn dựa trên vị trí chuột trong sự kiện drop
                    const dropIndex = Array.from(evt.target.closest('.drop-zone').childNodes).indexOf(evt.target);

                    // Chèn phần tử vào danh sách đích
                    toList.items.splice(dropIndex, 0, item);

                    console.log(`Moved item with ID ${itemID} from list ${fromIndex} to list ${toIndex}`);
                }
            }
        },
        editItem(item) {
            const index = this.lists.findIndex(list => list.items.includes(item));
            const itemIndex = this.lists[index].items.findIndex(i => i.id === item.id);
            this.$set(this.lists[index].items, itemIndex, {
                ...item,
                title: this.newItemTitle.trim()
            });
            this.newItemTitle = '';
        },
        deleteItem(item) {
            const index = this.lists.findIndex(list => list.items.includes(item));
            const itemIndex = this.lists[index].items.findIndex(i => i.id === item.id);
            this.lists[index].items.splice(itemIndex, 1);
        },
        addList() {
            if (this.newTitle.trim() === '') return;
            const newList = {
                title: this.newTitle.trim(),
                items: []
            };
            this.lists.push(newList);
            this.newTitle = '';
        },
        saveListTitle(index) {
            this.lists[index].title = this.lists[index].title.trim();
        },
        deleteList(index) {
            this.lists.splice(index, 1);
        },
        showAddItemForm() {
            this.isAddingItem = true;
        },
        addItem(index) {
            if (this.newItemTitle.trim() === '') return;
            const newItem = {
                id: this.lists[index].items.length,
                title: this.newItemTitle.trim()
            };
            this.lists[index].items.push(newItem);
            this.newItemTitle = '';
            this.isAddingItem = false;
        },
        cancelAddItem() {
            this.isAddingItem = false;
            this.newItemTitle = '';
        },
        dragOver(toIndex, item) {
            const itemIndex = this.lists[toIndex].items.findIndex(i => i.id === item.id);
            this.dropIndex = itemIndex + 1; // Chèn sau phần tử được hover
        },
    }
};
</script>

<style scoped>
.kanban-board {
    display: flex;
}

.kanban-list {
    background-color: #ebecf0;
    margin: 0 10px;
    min-width: 250px;
    border-radius: 3px;
}

.list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    background-color: #ebecf0;
    border-bottom: 1px solid #ccc;
}

.list-title-input {
    border: none;
    background: transparent;
    font-size: 16px;
    font-weight: bold;
    width: calc(100% - 20px);
}

.drop-zone {
    padding: 10px;
}

.drag-el {
    background-color: #fff;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 3px;
    cursor: pointer;
    position: relative;
}

.drag-el .edit,
.drag-el .delete {
    position: absolute;
    top: 5px;
    right: 5px;
    cursor: pointer;
}

.add-card {
    background-color: #ccc;
    padding: 10px;
    cursor: pointer;
    text-align: center;
    color: #fff;
    border-radius: 4px;
    margin-top: 5px;
}

.add-list {
    background-color: #ebecf0;
    padding: 10px;
    cursor: pointer;
    text-align: center;
    color: #333;
    border-radius: 3px;
    margin: 10px;
}

.delete-list {
    cursor: pointer;
    color: #999;
}

</style>
