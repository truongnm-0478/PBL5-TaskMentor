<template>
    <div class="categories">
        <a-space :size="[0, 8]" wrap>
            <span style="margin-right: 8px">Stage:</span>
            <a-checkable-tag
                v-for="sprint in planning"
                :key="sprint.stage"
                :checked="isSelected(sprint.stage)"
                @change="handleChange(sprint.stage)"
            >
                {{ sprint.stage }}
            </a-checkable-tag>
        </a-space>
        <a-button @click="showDrawer" type="primary">Create task
            <PlusOutlined/>
        </a-button>
    </div>
    <div class="kanban-board">
        <div v-for="(list, index) in lists" :key="index" class="kanban-list" :data-index="index">
            <div :class="['list-header', list.type]">
                <input v-model="list.title" @blur="saveListTitle(index)" class="list-title-input"/>
            </div>
            <div class="drop-zone" @drop="onDrop($event, index)" @dragover.prevent @dragenter.prevent>
                <div :key="item.id"
                     v-for="(item, itemIndex) in filteredItems(index)"
                     :draggable="true"
                     @dragstart="startDrag($event, item)"
                     @dragover="dragOver(index, item)"
                     @click="editSelectedItem(item)"
                     class="card"
                >
                    <p style="font-weight: 500; color: #2B3441">{{ item.description }}</p>
                    <a-tag :bordered="false" :color="getTagColor(item.task_name)">
                        <strong style="text-transform: uppercase; font-size: 10px">
                            {{ item.task_name }}
                        </strong>
                    </a-tag>
                    <div style="display: flex; justify-content: space-between; align-items: center">
                        <div class="stage">
                            <TagTwoTone/>
                            Stage {{ item.sprint_id }}
                        </div>
                        <div class="user" v-if="item.userName != null">
                            <a-avatar size="small" :style="{ backgroundColor: getColorForLastLetter(item.userName) }">{{ getLastLetter(item.userName) }}</a-avatar>
                        </div>
                    </div>
                </div>
                <!-- Add -->
                <a-card v-if="isAddingItem === index">
                    <a-input type="text" v-model="newItemTitle" placeholder="Enter title" style="margin-bottom: 10px"/>
                    <a-button @click="addItem(index)" style="width: 49%; margin-right: 1px">Add</a-button>
                    <a-button @click="cancelAddItem" style="width: 50%">Cancel</a-button>
                </a-card>
                <div v-else @click="showAddItemForm(index)" class="create">
                    <PlusOutlined/>
                    Create issue
                </div>
            </div>
        </div>
    </div>
    <a-drawer
        title="Create a new account"
        :width="720"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <a-form :model="form" :rules="rules" layout="vertical" ref="formRef">
            <a-row :gutter="16">
                <a-col :span="12">
                    <a-form-item label="Epic" name="name">
                        <a-input v-model:value="form.name" placeholder="Please enter epic"/>
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="Status" name="status">
                        <a-select v-model:value="form.status" placeholder="Please choose the status">
                            <a-select-option v-for="(statusLabel, statusValue) in statusMapping" :key="statusValue" :value="statusValue">
                                {{ statusLabel }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="12">
                    <a-form-item label="Sprint" name="sprint">
                        <a-select v-model:value="form.sprint" placeholder="Please choose the sprint">
                            <a-select-option v-for="sprint in planning" :key="sprint.id" :value="sprint.id">
                                Stage {{ sprint.stage }}: {{ sprint.name }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="Deadline" name="deadline">
                        <a-date-picker
                            v-model:value="form.deadline"
                            style="width: 100%"
                            :get-popup-container="trigger => trigger.parentElement"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="24">
                    <a-form-item label="Description" name="description">
                        <a-textarea
                            v-model:value="form.description"
                            :rows="4"
                            placeholder="please enter url description"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
        <template #extra>
            <a-space>
                <a-button @click="onClose">Cancel</a-button>
                <a-button type="primary" @click="handleSubmit">Submit</a-button>
            </a-space>
        </template>
    </a-drawer>
    <a-drawer
        :width="720"
        :open="openEdit"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onCloseEdit"
    >
        <a-form :model="formEdit" :rules="rules" layout="vertical" ref="formRef">
            <a-row :gutter="16">
                <a-col :span="12">
                    <a-form-item label="Epic" name="name">
                        <a-input v-model:value="formEdit.name" placeholder="Please enter epic"/>
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="Assignee" name="assignedTo">
                        <a-select
                            v-model:value="formEdit.assignedTo"
                            show-search
                            placeholder="Select a member"
                            style="width: 100%"
                            :options="options"
                        ></a-select>
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="12">
                    <a-form-item label="Sprint" name="sprint">
                        <a-select v-model:value="formEdit.sprint" placeholder="Please choose the sprint">
                            <a-select-option v-for="sprint in planning" :key="sprint.id" :value="sprint.id">
                                Stage {{ sprint.stage }}: {{ sprint.name }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="Deadline" name="deadline">
                        <a-date-picker
                            v-model:value="formEdit.deadline"
                            style="width: 100%"
                            :get-popup-container="trigger => trigger.parentElement"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="24">
                    <a-form-item label="Description" name="description">
                        <a-textarea
                            v-model:value="formEdit.description"
                            :rows="4"
                            placeholder="please enter url description"
                        />
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
        <template #extra>
            <a-space>
                <a-button type="dashed" danger @click="deleteItem">Delete</a-button>
                <a-button @click="onCloseEdit">Cancel</a-button>
                <a-button type="primary" @click="handleSubmitEdit">Submit</a-button>
            </a-space>
        </template>
    </a-drawer>
</template>

<script setup>
import {computed, onMounted, ref, watch} from 'vue'
import {PlusOutlined, TagTwoTone, DeleteOutlined} from '@ant-design/icons-vue'
import taskApi from '@/repositories/taskApi.js'
import {reactive} from 'vue'
import projectApi from '@/repositories/projectApi.js'
import router from '@/router/index.js'
import {useMessageStore} from '@/stores/messageStore.js'
import dayjs from 'dayjs'
import { getColorForLastLetter } from '@/utils/colorUtils.js'
import { getLastLetter } from '@/utils/stringUtils.js'

const open = ref(false)
const openEdit = ref(false)
const form = reactive({
    name: '',
    status: null,
    sprint: '',
    deadline: null,
    description: '',
})
const planning = ref([])
const statusMapping = {
    0: 'To Do',
    1: 'In Progress',
    2: 'In Review',
    3: 'Done'
}

const showDrawer = () => {
    open.value = true
}
const onClose = () => {
    open.value = false
}

const getListSprint = () => {
    projectApi.getPlan(router.currentRoute.value.query.id)
        .then(res => {
            planning.value = res.data.map(sprint => ({
                id: sprint.sprintId,
                stage: sprint.stage,
                name: sprint.name,
            }))
        })
        .catch(err => {
            console.log("ERR: ", err)
        })
}
onMounted(() => {
    getListSprint()
})

const rules = {
    name: [
        {
            required: true,
            message: 'Please enter epic',
        },
    ],
    status: [
        {
            required: true,
            message: 'Please choose the status',
        },
    ],
    sprint: [
        {
            required: true,
            message: 'Please choose the sprint',
        },
    ],
    deadline: [
        {
            required: true,
            message: 'Please choose the deadline',
            type: 'object',
        },
    ],
    description: [
        {
            required: true,
            message: 'Please enter url description',
        },
    ],
}

const formRef = ref(null)

const handleSubmit = () => {
    formRef.value.validate().then(() => {
        taskApi.addTask(form)
            .then(res => {
                useMessageStore().addMessage('success', 'Create successfully!')
                form.name = ''
                form.status = null
                form.sprint = ''
                form.deadline = null
                form.description = ''
            })
            .catch(err => {
                useMessageStore().addMessage('error', 'Create failure!')
            })
        getListTasks()
        onClose()
    }).catch((error) => {
        console.error('Validation Error:', error)
    })
}

// Khai báo lists
const lists = ref([
    { title: 'To do', type: 'todo', items: [] },
    { title: 'In progress', type: 'progress', items: [] },
    { title: 'In review', type: 'review', items: [] },
    { title: 'Completed', type: 'completed', items: [] }
])

const getListTasks = () => {
    lists.value.forEach(list => {
        list.items = [];
    });

    taskApi.getListTaskByTeamId(router.currentRoute.value.query.id)
        .then(res => {
            res.data.forEach(task => {
                const listIndex = task.status;
                if (listIndex >= 0 && listIndex < lists.value.length) {
                    lists.value[listIndex].items.push({
                        id: task.id,
                        sprintName: task.sprintName,
                        sprint_id: task.sprintId,
                        task_name: task.taskName,
                        stage: task.stage,
                        description: task.description,
                        status: task.status,
                        userName: task.userName
                    });
                }
            });
        })
        .catch(err => {
            console.error("Error fetching task list:", err)
        });
}
getListTasks()

const newItemTitle = ref('');
const newTitle = ref('');
const isAddingItem = ref(null);
const dropIndex = ref(-1);

const tagColors = {
    A: 'processing',
    B: 'volcano',
    C: 'warning',
    D: 'error',
    G: 'cyan',
    M: 'success',
    N: 'magenta',
    H: 'orange',
    I: 'volcano',
    K: 'gold',
    L: 'lime',
    O: 'green',
    P: 'red',
    Q: 'blue',
    R: 'geekblue',
    S: 'processing',
    T: 'volcano',
    U: 'magenta',
    V: 'orange',
};


// Update status
const startDrag = (evt, item) => {
    evt.dataTransfer.dropEffect = 'move';
    evt.dataTransfer.effectAllowed = 'move';
    evt.dataTransfer.setData('itemID', item.id);
    evt.dataTransfer.setData(
        'fromIndex',
        parseInt(evt.target.closest('.kanban-list').getAttribute('data-index'))
    );
};

const updateItemStatus = (item, newStatus) => {
    let status = null
    switch (newStatus) {
        case 'todo':
            status = 0
            break
        case 'progress':
            status = 1
            break
        case 'review':
            status = 2
            break
        case 'completed':
            status = 3
            break
    }
    taskApi.updateTaskStatus({
        id: item.id,
        status: status
    })
        .then(res => {
            useMessageStore().addMessage('success', 'Successfully!')
            getListTasks()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Failure!')
        })
}

const onDrop = (evt, toIndex) => {
    const fromIndex = parseInt(evt.dataTransfer.getData('fromIndex'));
    const itemID = parseInt(evt.dataTransfer.getData('itemID'));
    const toList = lists.value[toIndex];
    const fromList = lists.value[fromIndex];

    if (fromList && toList) {
        const itemIndex = fromList.items.findIndex(item => item.id === itemID);
        const item = fromList.items.splice(itemIndex, 1)[0];
        const dropIndex = Array.from(evt.target.closest('.drop-zone').childNodes).indexOf(evt.target);

        toList.items.splice(dropIndex, 0, item);

        if (fromIndex !== toIndex) {
            updateItemStatus(item, toList.type);
        }
    }
}

const saveListTitle = (index) => {
    lists.value[index].title = lists.value[index].title.trim()
}

const showAddItemForm = (index) => {
    isAddingItem.value = index;
}

const addItem = (index) => {
    if (newItemTitle.value.trim() === '') return;
    const newItem = {
        id: lists.value[index].items.length,
        title: newItemTitle.value.trim()
    };
    lists.value[index].items.push(newItem);
    newItemTitle.value = '';
    isAddingItem.value = false;
};

const cancelAddItem = () => {
    isAddingItem.value = false;
    newItemTitle.value = '';
};

const dragOver = (toIndex, item) => {
    const itemIndex = lists.value[toIndex].items.findIndex(i => i.id === item.id)
    dropIndex.value = itemIndex + 1
};

const getTagColor = (taskName) => {
    const firstLetter = taskName.charAt(0).toUpperCase();
    return tagColors[firstLetter] || 'green'
}

// Edit
const options = ref([])
const formEdit = reactive({
    id: null,
    taskId: null,
    name: '',
    sprint: '',
    deadline: null,
    description: '',
    assignedTo: null,
})

const editSelectedItem = (item) => {
    taskApi.getAssignByTaskId(item.id)
        .then(res => {
            formEdit.name = res.data.taskName
            formEdit.sprint = res.data.sprintId
            formEdit.deadline = dayjs(res.data.deadline)
            formEdit.description = res.data.description
            formEdit.assignedTo = res.data.assignedTo === 0 ? null : res.data.assignedTo
            formEdit.id = res.data.id
            formEdit.taskId = res.data.taskId


            options.value = res.data.listAssigned.map(user => ({
                value: user.userId,
                label: user.name
            }))
        })
        .catch(err => {
            console.log("ERR: ", err)
        })
    openEdit.value = true
}

const onCloseEdit = () => {
    openEdit.value = false
}

const handleSubmitEdit = () => {
    taskApi.updateAssign(formEdit)
        .then(res => {
            useMessageStore().addMessage('success', 'Successfully!')
            openEdit.value = false
            getListTasks()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Failure!')
        })
}

const deleteItem = () => {
    taskApi.deleteTask(formEdit.taskId)
        .then(res => {
            useMessageStore().addMessage('success', 'Delete successfully!')
            getListTasks()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Delete failure!')
        })
    openEdit.value = false
}

// Categories
const selectedSprint = ref(null)

const handleChange = (stage) => {
    selectedSprint.value = selectedSprint.value === stage ? null : stage
}

const isSelected = (stage) => {
    return selectedSprint.value === stage
}

const filteredItems = (index) => {
    const list = lists.value[index];
    if (!selectedSprint.value) {
        return list.items;
    }
    return list.items.filter(item => item.stage === selectedSprint.value);
}
</script>

<style scoped>

.categories {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 10px 20px 10px;
}

.kanban-board {
    display: flex;
    overflow-x: auto;
    min-height: calc(100vh - 234px);
}

.kanban-list {
    flex: 1;
    min-width: 250px;
    background-color: var(--color-gray-light);
    margin: 0 10px 30px;
    border-radius: 3px;
    box-shadow: rgba(9, 30, 66, 0.13) 0 1px 2px;
}

.list-header {
    padding: 8px;
}

.list-title-input {
    border: none;
    background: transparent;
    font-size: 13px;
    color: #1A5DB6;
    text-transform: uppercase;
    font-weight: bold;
    width: calc(100% - 20px);
}

.card {
    background-color: var(--color-white);
    margin-bottom: 4px;
    padding: 10px;
    border-radius: 4px;
    box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.1);
    position: relative;
}

.card:hover {
    background-color: #E9F2FF;
}

.more_btn {
    display: none;
    position: absolute;
    right: 4px;
    top: 4px;
    justify-content: center;
    align-items: center;
    height: 24px;
}

.card:hover .more_btn {
    display: flex;
}

.stage {
    margin-top: 10px;
    font-weight: 600;
    color: #2B3441;
}

.create {
    font-weight: 600;
    color: #2B3441;
    cursor: pointer;
    padding: 10px;
    border-radius: 3px;
}

.create:hover {
    background-color: rgba(43, 52, 65, 0.1);
}

.drop-zone {
    padding: 10px;
    display: flex;
    flex-direction: column;
    align-items: stretch;
}

.drag-el {
    background-color: var(--color-blue);
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
    border-radius: 3px;
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
