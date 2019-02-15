import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVinculacionInstructor, defaultValue } from 'app/shared/model/vinculacion-instructor.model';

export const ACTION_TYPES = {
  FETCH_VINCULACIONINSTRUCTOR_LIST: 'vinculacionInstructor/FETCH_VINCULACIONINSTRUCTOR_LIST',
  FETCH_VINCULACIONINSTRUCTOR: 'vinculacionInstructor/FETCH_VINCULACIONINSTRUCTOR',
  CREATE_VINCULACIONINSTRUCTOR: 'vinculacionInstructor/CREATE_VINCULACIONINSTRUCTOR',
  UPDATE_VINCULACIONINSTRUCTOR: 'vinculacionInstructor/UPDATE_VINCULACIONINSTRUCTOR',
  DELETE_VINCULACIONINSTRUCTOR: 'vinculacionInstructor/DELETE_VINCULACIONINSTRUCTOR',
  RESET: 'vinculacionInstructor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVinculacionInstructor>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VinculacionInstructorState = Readonly<typeof initialState>;

// Reducer

export default (state: VinculacionInstructorState = initialState, action): VinculacionInstructorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VINCULACIONINSTRUCTOR):
    case REQUEST(ACTION_TYPES.UPDATE_VINCULACIONINSTRUCTOR):
    case REQUEST(ACTION_TYPES.DELETE_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR):
    case FAILURE(ACTION_TYPES.CREATE_VINCULACIONINSTRUCTOR):
    case FAILURE(ACTION_TYPES.UPDATE_VINCULACIONINSTRUCTOR):
    case FAILURE(ACTION_TYPES.DELETE_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VINCULACIONINSTRUCTOR):
    case SUCCESS(ACTION_TYPES.UPDATE_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VINCULACIONINSTRUCTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/vinculacion-instructors';

// Actions

export const getEntities: ICrudGetAllAction<IVinculacionInstructor> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR_LIST,
    payload: axios.get<IVinculacionInstructor>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVinculacionInstructor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VINCULACIONINSTRUCTOR,
    payload: axios.get<IVinculacionInstructor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVinculacionInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VINCULACIONINSTRUCTOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVinculacionInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VINCULACIONINSTRUCTOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVinculacionInstructor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VINCULACIONINSTRUCTOR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
