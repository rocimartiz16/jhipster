import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDiaJornada, defaultValue } from 'app/shared/model/dia-jornada.model';

export const ACTION_TYPES = {
  FETCH_DIAJORNADA_LIST: 'diaJornada/FETCH_DIAJORNADA_LIST',
  FETCH_DIAJORNADA: 'diaJornada/FETCH_DIAJORNADA',
  CREATE_DIAJORNADA: 'diaJornada/CREATE_DIAJORNADA',
  UPDATE_DIAJORNADA: 'diaJornada/UPDATE_DIAJORNADA',
  DELETE_DIAJORNADA: 'diaJornada/DELETE_DIAJORNADA',
  RESET: 'diaJornada/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDiaJornada>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DiaJornadaState = Readonly<typeof initialState>;

// Reducer

export default (state: DiaJornadaState = initialState, action): DiaJornadaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIAJORNADA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIAJORNADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DIAJORNADA):
    case REQUEST(ACTION_TYPES.UPDATE_DIAJORNADA):
    case REQUEST(ACTION_TYPES.DELETE_DIAJORNADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DIAJORNADA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIAJORNADA):
    case FAILURE(ACTION_TYPES.CREATE_DIAJORNADA):
    case FAILURE(ACTION_TYPES.UPDATE_DIAJORNADA):
    case FAILURE(ACTION_TYPES.DELETE_DIAJORNADA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAJORNADA_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIAJORNADA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIAJORNADA):
    case SUCCESS(ACTION_TYPES.UPDATE_DIAJORNADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIAJORNADA):
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

const apiUrl = 'api/dia-jornadas';

// Actions

export const getEntities: ICrudGetAllAction<IDiaJornada> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DIAJORNADA_LIST,
    payload: axios.get<IDiaJornada>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDiaJornada> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIAJORNADA,
    payload: axios.get<IDiaJornada>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDiaJornada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIAJORNADA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDiaJornada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIAJORNADA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDiaJornada> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIAJORNADA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
